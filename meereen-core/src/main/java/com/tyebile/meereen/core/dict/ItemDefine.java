package com.tyebile.meereen.core.dict;

import java.util.List;

/**
 * @author zhouhao
 * @since 3.0
 */
public interface ItemDefine {
    String getText();

    String getValue();

    String getComments();

    List<ItemDefine> getChildren();

}
