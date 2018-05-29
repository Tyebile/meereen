package com.tyebile.meereen.commons.entity;

/**
 * @author zhouhao
 */
public interface CloneableEntity extends Entity, Cloneable {
    CloneableEntity clone();
}
