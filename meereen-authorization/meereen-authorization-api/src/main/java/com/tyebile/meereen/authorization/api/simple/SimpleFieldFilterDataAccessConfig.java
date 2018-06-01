package com.tyebile.meereen.authorization.api.simple;

import com.tyebile.meereen.authorization.api.access.FieldFilterDataAccessConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.tyebile.meereen.authorization.api.access.DataAccessConfig.DefaultType.DENY_FIELDS;

/**
 * 默认配置实现
 *
 * @author zhouhao
 * @see FieldFilterDataAccessConfig
 * @since 3.0
 */
public class SimpleFieldFilterDataAccessConfig extends AbstractDataAccessConfig implements FieldFilterDataAccessConfig {
    private static final long serialVersionUID = 8080660575093151866L;

    private Set<String> fields;

    public SimpleFieldFilterDataAccessConfig() {
    }

    public SimpleFieldFilterDataAccessConfig(String... fields) {
        this.fields = new HashSet<>(Arrays.asList(fields));
    }

    @Override
    public Set<String> getFields() {
        return fields;
    }

    public void setFields(Set<String> fields) {
        this.fields = fields;
    }

    @Override
    public String getType() {
        return DENY_FIELDS;
    }
}
