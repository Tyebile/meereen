package com.tyebile.meereen.expands.script;

/**
 * Created by zhouhao on 16-6-27.
 */
public class ScriptContext {
    private String id;

    private String md5;

    public ScriptContext(String id, String md5) {
        this.id = id;
        this.md5 = md5;
    }

    public String getMd5() {
        return md5;
    }

    public String getId() {
        return id;
    }
}
