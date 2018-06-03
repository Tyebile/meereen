package com.tyebile.meereen.datasource.api.service;

import com.tyebile.meereen.datasource.api.DynamicDataSource;
import com.tyebile.meereen.datasource.api.DynamicDataSourceProxy;
import com.tyebile.meereen.datasource.api.config.DynamicDataSourceConfigRepository;
import com.tyebile.meereen.datasource.api.config.InSpringDynamicDataSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

/**
 * 基于spring容器的动态数据源服务。从spring容器中获取数据源
 *
 * @author zhouhao
 * @since 3.0
 */
public class InSpringContextDynamicDataSourceService extends AbstractDynamicDataSourceService<InSpringDynamicDataSourceConfig> {

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public InSpringContextDynamicDataSourceService(DynamicDataSourceConfigRepository<InSpringDynamicDataSourceConfig> repository, DynamicDataSource defaultDataSource) {
        super(repository, defaultDataSource);
    }

    public InSpringContextDynamicDataSourceService(DynamicDataSourceConfigRepository<InSpringDynamicDataSourceConfig> repository, DataSource dataSource) throws SQLException {
        super(repository, dataSource);
    }

    @Override
    protected DataSourceCache createCache(InSpringDynamicDataSourceConfig config) {
        DataSource dataSource = applicationContext.getBean(config.getBeanName(), DataSource.class);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            return new DataSourceCache(config.hashCode(),
                    new DynamicDataSourceProxy(config.getId(), dataSource),
                    countDownLatch,
                    config);
        } finally {
            countDownLatch.countDown();
        }
    }

}
