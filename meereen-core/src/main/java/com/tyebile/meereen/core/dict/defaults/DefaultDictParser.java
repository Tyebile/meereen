package com.tyebile.meereen.core.dict.defaults;

import com.tyebile.meereen.core.dict.DictDefine;
import com.tyebile.meereen.core.dict.DictParser;
import com.tyebile.meereen.core.dict.ItemDefine;

/**
 * @author zhouhao
 * @since 3.0
 */
public class DefaultDictParser implements DictParser {
    @Override
    public String getId() {
        return "default";
    }

    @Override
    public String parseText(DictDefine dictDefine, String value) {
        return dictDefine.getItems()
                .stream()
                .filter(itemDefine -> itemDefine.getValue().equals(value))
                .map(ItemDefine::getText)
                .findFirst()
                .orElse(value);
    }

    @Override
    public String parseValue(DictDefine dictDefine, String text) {
        return dictDefine.getItems()
                .stream()
                .filter(itemDefine -> itemDefine.getText().equals(text))
                .map(ItemDefine::getValue)
                .findFirst()
                .orElse(text);
    }
}
