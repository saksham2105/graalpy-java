package com.exxeta.projectmatcher.controller;

import com.exxeta.projectmatcher.evaluator.PythonScriptEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptException;
import java.util.Map;

@RestController
@Slf4j
public class CodeController {

    @Autowired
    private PythonScriptEvaluator pythonScriptEvaluator;

    @PostMapping("/api/code")
    public String codeProcess(@RequestBody Map<String, Object> codeMap) throws ScriptException {
        String code = (String) codeMap.get("code");
        Map<String, Object> inputs = (Map<String, Object>) codeMap.get("input");
        Value value = pythonScriptEvaluator.eval(code, inputs);
        return value.asString();
    }
}
