package com.ns.common.util.rule.context;

import java.util.HashMap;
import java.util.Map;

public class RuleContext {

    private Map<String, Object> parameter = new HashMap<String, Object>();

    private Map<String, Object> result = new HashMap<String, Object>();

    protected static final ThreadLocal<? extends RuleContext> threadLocal = new ThreadLocal<RuleContext>() {
        @Override
        protected RuleContext initialValue() {
            return new RuleContext();
        }
    };

    public static RuleContext getCurrentContext() {
        RuleContext context = threadLocal.get();
        return context;
    }

    public void addParameter(String name, Object value) {
        parameter.put(name, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getParameter(String name) {
        return (T) parameter.get(name);
    }

    public Map<String, Object> getParameters() {
        return parameter;
    }

    public Map<String, Object> getResults() {
        return result;
    }

    public void addResult(String name, Object result) {
        this.result.put(name, result);
    }

    public void unset() {
        threadLocal.remove();
    }

}