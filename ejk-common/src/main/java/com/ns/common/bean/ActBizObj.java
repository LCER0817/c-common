package com.ns.common.bean;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuezhucao on 2017/11/16.
 */
public class ActBizObj<Obj> {
    private Obj obj;
    private Map<String, Object> dataMap;

    public ActBizObj(Obj obj) {
        this.obj = obj;
        this.dataMap = new HashMap<>(10);
    }

    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> data) {
        this.dataMap = data;
    }

    public Object getData(String key) {
        return dataMap.get(key);
    }

    public void setData(String key, Object value) {
        dataMap.put(key, value);
    }

    public static <Obj> List<ActBizObj<Obj>> getActBizObjs(List<Obj> objs) {
        if (CollectionUtils.isEmpty(objs)) {
            return new ArrayList<>(0);
        }

        List<ActBizObj<Obj>> result = new ArrayList<>(objs.size());
        for (Obj obj : objs) {
            result.add(new ActBizObj<>(obj));
        }

        return result;
    }
}
