package com.tyebile.meereen.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.tyebile.meereen.core.dict.EnumDict;

@AllArgsConstructor
@Getter
public enum DataStatusEnum implements EnumDict<Byte> {
    ENABLED((byte) 1, "正常"),
    DISABLED((byte) 0, "禁用"),
    LOCK((byte) -1, "锁定"),
    DELETED((byte) -10, "删除");

    private Byte value;

    private String text;

}
