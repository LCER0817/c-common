package com.ns.common.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by liqiuwei on 2017/6/9.
 * 系统参数配置表
 */
@Entity
public class Param {
    @Id
    private String id;
    /**
     * 父节点名称
     */
    private String parentName;
    /**
     * key名称
     */
    private String name;
    /**
     * 备注
     */
    private String comment;
    /**
     * 值（json）
     */
    private String value;
    /**
     * 有效状态。1：有效、0：无效
     */
    private Integer validStatus;

    private Date createTime;
    @Column(insertable = false)
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Param{" +
                "id=" + id +
                ", parentName='" + parentName + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", value='" + value + '\'' +
                ", validStatus=" + validStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}