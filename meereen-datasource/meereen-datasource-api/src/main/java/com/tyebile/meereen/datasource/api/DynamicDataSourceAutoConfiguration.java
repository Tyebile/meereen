package com.tyebile.meereen.datasource.api;

import com.tyebile.meereen.orm.rdb.executor.SqlExecutor;
import com.tyebile.meereen.datasource.api.config.DynamicDataSourceConfigRepository;
import com.tyebile.meereen.datasource.api.config.InSpringDynamicDataSourceConfig;
import com.tyebile.meereen.datasource.api.service.InSpringContextDynamicDataSourceService;
import com.tyebile.meereen.datasource.api.service.InSpringDynamicDataSourceConfigRepository;
import com.tyebile.meereen.datasource.api.switcher.DataSourceSwitcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhouhao
 */
@Configuration
@ImportAutoConfiguration(AopDataSourceSwitcherAutoConfiguration.class)
public class DynamicDataSourceAutoConfiguration implements BeanPostProcessor {

    @Bean
    @ConditionalOnMissingBean(SqlExecutor.class)
    public SqlExecutor sqlExecutor() {
        return new DefaultJdbcExecutor();
    }

    @Bean
    @ConditionalOnMissingBean(DynamicDataSourceConfigRepository.class)
    public InSpringDynamicDataSourceConfigRepository inSpringDynamicDataSourceConfigRepository() {
        return new InSpringDynamicDataSourceConfigRepository();
    }

    @Bean
    @ConditionalOnMissingBean(DynamicDataSourceService.class)
    public InSpringContextDynamicDataSourceService inMemoryDynamicDataSourceService(DynamicDataSourceConfigRepository<InSpringDynamicDataSourceConfig> repository,
                                                                                    DataSource dataSource) {
        DynamicDataSourceProxy dataSourceProxy = new DynamicDataSourceProxy(null, dataSource);
        return new InSpringContextDynamicDataSourceService(repository, dataSourceProxy);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DynamicDataSourceService) {
            DataSourceHolder.dynamicDataSourceService = ((DynamicDataSourceService) bean);
        }
        if (bean instanceof DataSourceSwitcher) {
            DataSourceHolder.dataSourceSwitcher = ((DataSourceSwitcher) bean);
        }
        return bean;
    }


    @Configuration
    public static class AutoRegisterDataSource {
        @Autowired
        public void setDataSourceService(DynamicDataSourceService dataSourceService) {
            DataSourceHolder.dynamicDataSourceService = dataSourceService;
        }
    }

}
