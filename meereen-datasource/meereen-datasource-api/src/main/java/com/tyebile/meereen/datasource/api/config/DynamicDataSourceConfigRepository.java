package com.tyebile.meereen.datasource.api.config;

import java.util.List;

public interface DynamicDataSourceConfigRepository<C extends DynamicDataSourceConfig> {
    List<C> findAll();

    C findById(String dataSourceId);

    C add(C config);

    C remove(String dataSourceId);
}
