package com.tyebile.meereen.orm.rdb.simple.trigger;

import org.hswebframework.expands.script.engine.DynamicScriptEngine;
import org.hswebframework.expands.script.engine.ExecuteResult;
import com.tyebile.meereen.orm.core.Trigger;
import com.tyebile.meereen.orm.rdb.exception.TriggerException;
import com.tyebile.meereen.utils.StringUtils;

import java.util.Map;

public class ScriptTraggerSupport implements Trigger {
    private String scriptId;

    private DynamicScriptEngine engine;

    public ScriptTraggerSupport(DynamicScriptEngine engine, String scriptId) {
        this.engine = engine;
        this.scriptId = scriptId;
    }

    @Override
    public void execute(Map<String, Object> context) throws TriggerException {
        boolean scriptCompiled = engine.compiled(scriptId);
        if (!scriptCompiled) {
            throw new TriggerException("动态脚本 [" + scriptId + "] 未编译!");
        }
        ExecuteResult result = engine.execute(scriptId, context);
        if (result.isSuccess()) {
            Object rsl = result.get();
            if (rsl instanceof Boolean) {
                if (!((Boolean) rsl)) {
                    throw new TriggerException("脚本返回结果:false");
                }
            }
            if (rsl instanceof Map) {
                Map map = ((Map) rsl);
                if (!StringUtils.isTrue(map.get("success"))) {
                    throw new TriggerException(String.valueOf(map.get("message")));
                }
            }
        } else {
            Throwable throwable = result.getException();
            while (throwable != null && (throwable = throwable.getCause()) != null) {
                if (throwable instanceof RuntimeException) {
                    throw (RuntimeException) throwable;
                }
            }
            throw new TriggerException(result.getMessage(), result.getException());
        }
    }
}
