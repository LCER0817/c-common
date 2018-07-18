package com.ns.common.util.rule;

public class RuleNode {
    private String name;
    private String leftName;
    private String rightName;

    public RuleNode() {
        super();
    }

    public RuleNode(String name, String leftName, String rightName) {
        super();
        this.name = name;
        this.leftName = leftName;
        this.rightName = rightName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    @Override
    public String toString() {
        return "RuleNode [name=" + name + ", leftName=" + leftName + ", rightName=" + rightName + "]";
    }

}
