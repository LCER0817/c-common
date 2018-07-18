package com.ns.common.util.rule;

import java.util.LinkedHashMap;
import java.util.Map;

public class RuleTree {

    private Map<String, RuleNode> nodeMap = new LinkedHashMap<String, RuleNode>();

    public RuleNode putRule(String name, RuleNode ruleNode) {
        return nodeMap.put(name, ruleNode);
    }

    public RuleNode getRootVNode() {
        return nodeMap.values().iterator().next();
    }

    public RuleNode getRule(String name) {
        return nodeMap.get(name);
    }

}
