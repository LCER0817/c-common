package com.ns.common.util.rule;

import com.ns.common.util.constant.Rule;
import com.ns.common.util.exception.sys.ParameterException;
import com.ns.common.util.rule.context.IRuleContextHandler;
import com.ns.common.util.rule.context.RuleBeanContext;
import com.ns.common.util.rule.context.RuleBeanContextFactory;
import com.ns.common.util.rule.context.RuleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleMgr {

    private static final Logger logger = LoggerFactory.getLogger(RuleMgr.class);


    public static void run(String ruleName, IRuleContextHandler resultHandler) {
        resultHandler.init(RuleContext.getCurrentContext());
        RuleTree tree = RuleTreeUtil.getTree(ruleName);
        if (null == tree) {
            throw new ParameterException("规则文件不存在: " + ruleName + ".conf");
        }
        RuleNode root = tree.getRootVNode();
        RuleBeanContext beanContext = RuleBeanContextFactory.getInstance();
        String name = root.getName();
        try {
            process(name, root, tree, beanContext);
            resultHandler.afterRun(RuleContext.getCurrentContext());
        } finally {
            // 清除上下文
            RuleContext.getCurrentContext().unset();
        }
    }

    private static void process(String name, RuleNode node, RuleTree tree, RuleBeanContext beanContext) {

        if ("end".equalsIgnoreCase(name)) {
            logger.debug("rule process end");
            return;
        }
        IRuleBiz validateBiz = beanContext.getBean(name);
        if (null == validateBiz) {
            throw new ParameterException("不存在name为：" + name + "的规则");
        }
        Rule result = validateBiz.run();
        if (null == node) {
            return;
        }
        if (Rule.YES == result) {
            name = node.getLeftName();
        } else if (Rule.NO == result) {
            name = node.getRightName();
        } else if (Rule.TERMINATED == result) {
            logger.debug("TERMINATED");
            return;
        } else {
            logger.warn("不支持的返回: {}", result);
            return;
        }
        node = tree.getRule(name);
        process(name, node, tree, beanContext);
    }

}
