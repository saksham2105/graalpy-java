package com.exxeta.projectmatcher.configuration;

import com.exxeta.projectmatcher.evaluator.PythonScriptEvaluator;
import com.exxeta.projectmatcher.service.RecommendationService;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PythonConfiguration {
    ResourceLoader resourceLoader;
    PythonScriptEvaluator pythonScriptEvaluator;

    private static String pythonPath = "classpath:com/exxeta/projectmatcher/service";

    public PythonConfiguration(ResourceLoader resourceLoader, PythonScriptEvaluator pythonScriptEvaluator) {
        this.pythonScriptEvaluator = pythonScriptEvaluator;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public RecommendationService getRecommendationService() throws IOException {
        Context context = Context
            .newBuilder("python")
            .allowAllAccess(true)
            .option("python.ForceImportSite", "true")
            .option("python.PythonPath",
                    resourceLoader
                            .getResource(pythonPath)
                            .getFile()
                            .toPath()
                            .toString()
            )
            .build();

        Source source = Source
                .newBuilder("python",
                resourceLoader.getResource(pythonPath + "/RecommendationServiceImpl.py").getFile()
        ).build();

        context.eval(source);

        return context
                .getBindings("python")
                .getMember("RecommendationServiceImpl")
                .as(RecommendationService.class);
    }
    @Bean
    public Value pythonCodeBean() throws ScriptException {
        // Define the corrected Python script
        String pythonScript = "def greet(name):\n" +
                "    print('I am greeting function')\n" +
                "    return f'Hello, {name}!'\n" +
                "\n" +
                "x = number  # Correctly use the number variable\n" +
                "print(\"The number is \" + str(x + 2))\n" +
                "message = f\"{name}\"\n" +
                "result = greet(message)  # Store the result of the greet function\n" +
                "result";  // Return the result of the greet function

        // Define the input variable
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Saksham");
        input.put("number", 101);
        // Evaluate the Python script and retrieve the result
        Value value = this.pythonScriptEvaluator.eval(pythonScript, input);
        System.out.println("Value is : " + value);
        return value;
    }
}
