package com.tyebile.meereen.authorization.api.simple.builder;

import com.tyebile.meereen.authorization.api.access.DataAccessConfig;

/**
 * @author zhouhao
 */
public interface DataAccessConfigConvert {

    boolean isSupport(String type, String action, String config);

    DataAccessConfig convert(String type, String action, String config);
}
