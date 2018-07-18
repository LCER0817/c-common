package com.ns.common.util.rule.context;

import com.ns.common.util.rule.IRuleBiz;

import java.util.HashMap;
import java.util.Map;

public class NewRuleBeanContext implements RuleBeanContext {

    private static final Map<String, IRuleBiz> BEANS = new HashMap<String, IRuleBiz>();

    static {
        // init
    }

    @Override
    public IRuleBiz getBean(String vName) {
        return BEANS.get(vName);
    }

}
