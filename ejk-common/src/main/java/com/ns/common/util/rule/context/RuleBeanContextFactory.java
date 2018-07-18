package com.ns.common.util.rule.context;

public class RuleBeanContextFactory {

    private static final RuleBeanContext DEFAULT_CONTEXT = new NewRuleBeanContext();

    private static final RuleBeanContext SPRING_CONTEXT = new SpringRuleBeanContext();

    public static RuleBeanContext getInstance() {

        return RuleBeanContextFactory.SPRING_CONTEXT;
    }

}
