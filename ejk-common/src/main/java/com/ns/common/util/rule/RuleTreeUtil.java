package com.ns.common.util.rule;

import com.ns.common.util.exception.sys.RulePatternException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleTreeUtil implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(RuleTreeUtil.class);
    private static final String VNODE_SEPARATOR = ":";
    private static final String RESULT_SEPARATOR = ",";
    private static final String CONF_LOCATION = ResourceUtils.CLASSPATH_URL_PREFIX + "rule/*.conf";
    private static final String LINE_PATTERN = "nameA:nameB,nameC";
    private ApplicationContext applicationContext;

    private static final Map<String, RuleTree> RULE_TREE_MAP = new HashMap<>();


    public static RuleTree getTree(String name) {
        return RULE_TREE_MAP.get(name);
    }

    @PostConstruct
    public void init() {

        ResourcePatternResolver resourcePatternResolver = applicationContext;
        Resource[] resources = null;
        try {
            resources = resourcePatternResolver.getResources(CONF_LOCATION);
        } catch (IOException e) {
            // 忽略部分异常，记录日志
            logger.warn("读取{}路径下规则配置文件异常", CONF_LOCATION, e);
        }
        if (null != resources && resources.length > 0) {
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                try {
                    InputStream is = resource.getInputStream();
                    RuleTree ruleTree = InputStream2RuleTree(is, filename);
                    if (null != ruleTree) {
                        RULE_TREE_MAP.put(StringUtils.stripFilenameExtension(filename), ruleTree);
                    } else {
                        // 规则文件为空，或者读取失败，记录日志
                        logger.warn("规则文件转换为规则树失败: {}", filename);
                    }
                } catch (RulePatternException e) {
                    // 配置了规则文件就要正确所以抛出异常，异常信息提示错误的地方
                    throw e;
                } catch (Exception e) {
                    // 忽略部分异常，记录日志
                    logger.warn("读取{}路径下规则配置文件异常", CONF_LOCATION, e);
                }
            }
        } else {
            logger.warn("{}下不存在规则文件", CONF_LOCATION);
        }
    }

    private static RuleTree InputStream2RuleTree(InputStream is, String fileName) {
        List<String> readLines = null;
        try {
            readLines = IOUtils.readLines(is);
        } catch (Exception e) {
            logger.warn("解析规则配置文件异常", e);
        }
        if (CollectionUtils.isNotEmpty(readLines)) {
            RuleTree tree = new RuleTree();
            // 规则配置文件非空判断标识
            boolean flag = false;
            for (String line : readLines) {
                if (StringUtils.isEmpty(line)) {
                    continue;
                } else {
                    if (!flag) {
                        flag = true;
                    }
                    String[] nodes = line.split(VNODE_SEPARATOR);
                    if (nodes.length < 2) {
                        throw new RulePatternException(fileName, line, LINE_PATTERN);
                    } else {
                        String name = nodes[0].trim();
                        String results = nodes[1].trim();
                        String[] rf = results.split(RESULT_SEPARATOR);
                        if (rf.length < 2) {
                            throw new RulePatternException(fileName, line, LINE_PATTERN);
                        } else {
                            String leftName = rf[0].trim();
                            String rightName = rf[1].trim();
                            RuleNode ruleNode = new RuleNode(name, leftName, rightName);
                            tree.putRule(name, ruleNode);
                        }
                    }
                }
            }
            if (flag) {
                return tree;
            } else {
                // 规则文件为空，返回null
                logger.warn("规则配置文件:" + fileName + "为空");
                return null;
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
}
