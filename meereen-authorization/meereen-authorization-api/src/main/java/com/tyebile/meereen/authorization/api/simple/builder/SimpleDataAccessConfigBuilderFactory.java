package com.tyebile.meereen.authorization.api.simple.builder;

import com.alibaba.fastjson.JSON;
import com.tyebile.meereen.authorization.api.access.DataAccessConfig;
import com.tyebile.meereen.authorization.api.builder.DataAccessConfigBuilder;
import com.tyebile.meereen.authorization.api.builder.DataAccessConfigBuilderFactory;
import com.tyebile.meereen.authorization.api.simple.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import static com.tyebile.meereen.authorization.api.access.DataAccessConfig.DefaultType.*;

/**
 * @author zhouhao
 */
public class SimpleDataAccessConfigBuilderFactory implements DataAccessConfigBuilderFactory {

    private List<String> defaultSupportConvert = Arrays.asList(
            CUSTOM,
            OWN_CREATED,
            FIELD_SCOPE,
            DENY_FIELDS);

    private List<DataAccessConfigConvert> converts = new LinkedList<>();

    public SimpleDataAccessConfigBuilderFactory addConvert(DataAccessConfigConvert configBuilderConvert) {
        Objects.requireNonNull(configBuilderConvert);
        converts.add(configBuilderConvert);
        return this;
    }

    public void setDefaultSupportConvert(List<String> defaultSupportConvert) {
        this.defaultSupportConvert = defaultSupportConvert;
    }

    public List<String> getDefaultSupportConvert() {
        return defaultSupportConvert;
    }

    protected DataAccessConfigConvert createJsonConfig(String supportType, Class<? extends AbstractDataAccessConfig> clazz) {
        return createConfig(supportType, (action, config) -> JSON.parseObject(config, clazz));
    }


    protected DataAccessConfigConvert createConfig(String supportType, BiFunction<String, String, ? extends DataAccessConfig> function) {
        return new DataAccessConfigConvert() {
            @Override
            public boolean isSupport(String type, String action, String config) {
                return supportType.equals(type);
            }

            @Override
            public DataAccessConfig convert(String type, String action, String config) {
                DataAccessConfig conf = function.apply(action, config);
                if (conf instanceof AbstractDataAccessConfig) {
                    ((AbstractDataAccessConfig) conf).setAction(action);
                }
                return conf;
            }
        };
    }

    @PostConstruct
    public void init() {
        if (defaultSupportConvert.contains(FIELD_SCOPE)) {
            converts.add(createJsonConfig(FIELD_SCOPE, SimpleFiledScopeDataAccessConfig.class));
        }

        if (defaultSupportConvert.contains(DENY_FIELDS)) {
            converts.add(createJsonConfig(DENY_FIELDS, SimpleFieldFilterDataAccessConfig.class));
        }

        if (defaultSupportConvert.contains(OWN_CREATED)) {
            converts.add(createConfig(OWN_CREATED, (action, config) -> new SimpleOwnCreatedDataAccessConfig(action)));
        }

        if (defaultSupportConvert.contains(SCRIPT)) {
            converts.add(createJsonConfig(SCRIPT, SimpleScriptDataAccessConfig.class));
        }

        if (defaultSupportConvert.contains(CUSTOM)) {
            converts.add(createConfig(CUSTOM, (action, config) -> new SimpleCustomDataAccessConfigConfig(config)));
        }
    }

    @Override
    public DataAccessConfigBuilder create() {
        return new SimpleDataAccessConfigBuilder(converts);
    }
}
