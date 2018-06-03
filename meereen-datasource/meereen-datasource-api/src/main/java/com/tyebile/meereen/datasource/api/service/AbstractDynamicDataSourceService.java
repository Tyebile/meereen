package com.tyebile.meereen.datasource.api.service;

import com.tyebile.meereen.datasource.api.DynamicDataSource;
import com.tyebile.meereen.datasource.api.DynamicDataSourceProxy;
import com.tyebile.meereen.datasource.api.DynamicDataSourceService;
import com.tyebile.meereen.datasource.api.config.DynamicDataSourceConfig;
import com.tyebile.meereen.datasource.api.config.DynamicDataSourceConfigRepository;
import com.tyebile.meereen.datasource.api.exception.DataSourceNotFoundException;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhouhao
 */
public abstract class AbstractDynamicDataSourceService<C extends DynamicDataSourceConfig> implements DynamicDataSourceService {
    protected final Map<String, DataSourceCache> dataSourceStore = new ConcurrentHashMap<>(32);

    private final DynamicDataSource defaultDataSource;

    private DynamicDataSourceConfigRepository<C> repository;

    public void setRepository(DynamicDataSourceConfigRepository<C> repository) {
        this.repository = repository;
    }

    public AbstractDynamicDataSourceService(DynamicDataSourceConfigRepository<C> repository, DynamicDataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
        this.repository = repository;
    }

    public AbstractDynamicDataSourceService(DynamicDataSourceConfigRepository<C> repository, DataSource dataSource) throws SQLException {
        this(repository, new DynamicDataSourceProxy(null, dataSource));
    }

    @PreDestroy
    public void destroy() {
        dataSourceStore.values().forEach(DataSourceCache::closeDataSource);
    }

    @Override
    public DynamicDataSource getDataSource(String dataSourceId) {
        DataSourceCache cache = dataSourceStore.get(dataSourceId);
        C config = repository.findById(dataSourceId);
        if (config == null) {
            throw new DataSourceNotFoundException(dataSourceId, "数据源" + dataSourceId + "不存在");

        }
        if (cache == null) {
            cache = createCache(config);
            dataSourceStore.put(dataSourceId, cache);
            return cache.getDataSource();
        }
        if (cache.getHash() != config.hashCode()) {
            dataSourceStore.remove(dataSourceId);
            cache.closeDataSource();
            //重新获取
            return getDataSource(dataSourceId);
        }
        return cache.getDataSource();
    }

    @Override
    public DynamicDataSource getDefaultDataSource() {
        return defaultDataSource;
    }

    @Deprecated
    protected int getHash(String id) {
        return -1;
    }

    protected abstract DataSourceCache createCache(C config);

    @Deprecated
    protected DataSourceCache createCache(String id) {
        return null;
    }

    public DataSourceCache removeCache(String id) {
        return dataSourceStore.remove(id);
    }
}
