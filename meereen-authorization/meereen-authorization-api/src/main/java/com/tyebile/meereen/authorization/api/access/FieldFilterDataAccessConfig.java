package com.tyebile.meereen.authorization.api.access;

import java.util.Set;

/**
 * 对字段进行过滤操作配置,实现字段级别的权限控制
 *
 * @author zhouhao
 * @see DataAccessConfig
 * @see com.tyebile.meereen.authorization.api.simple.SimpleFieldFilterDataAccessConfig
 */
public interface FieldFilterDataAccessConfig extends DataAccessConfig {
    Set<String> getFields();
}
