package com.tyebile.meereen.orm.core;


public interface Validator {
    boolean validate(Object data, Operation operation);

    enum Operation {
        INSERT, UPDATE
    }
}
