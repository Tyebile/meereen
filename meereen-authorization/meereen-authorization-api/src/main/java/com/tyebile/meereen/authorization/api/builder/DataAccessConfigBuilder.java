package com.tyebile.meereen.authorization.api.builder;

import com.tyebile.meereen.authorization.api.access.DataAccessConfig;

/**
 *
 * @author zhouhao
 */
public interface DataAccessConfigBuilder {
    DataAccessConfigBuilder fromJson(String json);

    DataAccessConfig build();
}
