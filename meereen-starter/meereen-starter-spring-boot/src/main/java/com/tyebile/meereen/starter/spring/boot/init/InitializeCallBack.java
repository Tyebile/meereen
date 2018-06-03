package com.tyebile.meereen.starter.spring.boot.init;

import java.util.Map;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 */
public interface InitializeCallBack {
    void execute(Map<String, Object> context);
}
