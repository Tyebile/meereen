package com.tyebile.meereen.orm.core.param;

import java.util.HashMap;
import java.util.Map;

public class InsertParamMapParam extends InsertParam<Map<String, Object>> {

    @Override
    public Map<String, Object> getData() {
        if (super.getData() == null) setData(new HashMap<>());
        return super.getData();
    }

    public InsertParamMapParam value(String property, Object value) {
        getData().put(property, value);
        return this;
    }

    public InsertParamMapParam values(Map<String, Object> values) {
        getData().putAll(values);
        return this;
    }
}
