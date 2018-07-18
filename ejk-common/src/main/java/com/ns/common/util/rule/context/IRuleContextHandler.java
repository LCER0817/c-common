package com.ns.common.util.rule.context;

/**
 * Created by xuezhucao on 2017/10/26.
 */
public interface IRuleContextHandler {
    void init(RuleContext context);

    void afterRun(RuleContext context);
}
