package com.tyebile.meereen.core.dict;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouhao
 * @since 3.0
 */
public interface DictDefine extends Serializable {
    String getId();

    String getAlias();

    String getComments();

    String getParserId();

    List<ItemDefine> getItems();

}
