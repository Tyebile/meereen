package com.tyebile.meereen.orm.rdb.simple;

import com.tyebile.meereen.orm.core.SqlConditionSupport;
import com.tyebile.meereen.orm.core.TriggerSkipSupport;
import com.tyebile.meereen.orm.core.Validator;
import com.tyebile.meereen.orm.rdb.meta.RDBTableMetaData;

import java.util.Map;

public abstract class ValidatorAndTriggerSupport<O> extends SqlConditionSupport<O> implements TriggerSkipSupport<O> {
    protected boolean triggerSkip = false;

    void tryValidate(Object data, Validator.Operation operation) {
        Validator validator = getTableMeta().getValidator();
        if (validator != null) {
            validator.validate(data, operation);
        }
    }

    void trigger(String name, Map<String, Object> root) {
        RDBTableMetaData metaData = getTableMeta();
        metaData.on(name, root);
    }

    abstract RDBTableMetaData getTableMeta();

    @Override
    public O skipTrigger() {
        triggerSkip = true;
        return (O) this;
    }
}
