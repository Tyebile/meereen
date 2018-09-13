package com.tyebile.meereen.logging.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggerDefine {
    private String action;

    private String describe;

    public LoggerDefine(String action,String describe){
        this.action=action;
        this.describe=describe;
    }
}

