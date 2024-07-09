package com.exxeta.projectmatcher.evaluator;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

import javax.script.ScriptException;
import java.util.Map;

@Service
public class PythonScriptEvaluator {
    public Value eval(String script, Map<String, Object> input) throws ScriptException {
        Context context = Context
                .newBuilder("python")
                .allowAllAccess(true)
                .option("python.ForceImportSite", "true")
                .build();

        final Value pyBindings = context.getBindings("python");
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            pyBindings.putMember(entry.getKey(), entry.getValue());
        }
        try {
            Value value = context.eval("python", script);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScriptException(e);
        }
    }
}
