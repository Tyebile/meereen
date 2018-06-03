package com.tyebile.meereen.expands.script.js;


import org.apache.commons.codec.digest.DigestUtils;
import com.tyebile.meereen.expands.script.common.CommonScriptEngine;
import com.tyebile.meereen.utils.StringUtils;

import javax.script.CompiledScript;

/**
 * Created by 浩 on 2015-10-27 0027.
 */
public class JavaScriptEngine extends CommonScriptEngine {

    @Override
    public String getScriptName() {
        return "javascript";
    }

    @Override
    public boolean compile(String id, String code) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("compile {} {} : {}", getScriptName(), id, code);
        }
        if (compilable == null)
            init();
        CompiledScript compiledScript = compilable.compile(StringUtils.concat("(function(){", code, "\n})();"));
        CommonScriptContext scriptContext = new CommonScriptContext(id, DigestUtils.md5Hex(code), compiledScript);
        scriptBase.put(id, scriptContext);
        return true;
    }
}
