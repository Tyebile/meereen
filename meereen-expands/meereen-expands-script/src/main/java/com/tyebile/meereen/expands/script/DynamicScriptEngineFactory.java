package com.tyebile.meereen.expands.script;


import com.tyebile.meereen.expands.script.SpEL.SpElEngine;
import com.tyebile.meereen.expands.script.groovy.GroovyEngine;
import com.tyebile.meereen.expands.script.java.JavaEngine;
import com.tyebile.meereen.expands.script.js.JavaScriptEngine;
import com.tyebile.meereen.expands.script.ognl.OgnlEngine;
import com.tyebile.meereen.expands.script.python.PythonScriptEngine;
import com.tyebile.meereen.expands.script.ruby.RubyScriptEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 浩 on 2015-10-27 0027.
 */
public final class DynamicScriptEngineFactory {
    private static final Map<String, DynamicScriptEngine> map = new HashMap<>();

    static {
        JavaScriptEngine engine = new JavaScriptEngine();
        map.put("js", engine);
        map.put("javascript", engine);
        map.put("groovy", new GroovyEngine());
        map.put("ruby", new RubyScriptEngine());
        map.put("python", new PythonScriptEngine());
        try {
            map.put("java", new JavaEngine());
        } catch (Exception e) {

        }
        try {
            Class.forName("org.springframework.expression.ExpressionParser");
            map.put("spel", new SpElEngine());
        } catch (ClassNotFoundException e) {
        }
        try {
            Class.forName("ognl.Ognl");
            map.put("ognl", new OgnlEngine());
        } catch (ClassNotFoundException e) {
        }
    }

    public static final DynamicScriptEngine getEngine(String type) {
        return map.get(type);
    }

}
