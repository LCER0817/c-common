package com.ns.common.mgr;

import com.ns.common.bean.Param;
import com.ns.common.dao.ParamDao;
import com.ns.common.util.cache.AbsGuavaCache;
import com.ns.common.util.constant.ParamConstant;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/8/11.
 */
public class ParamMgr {
    @Autowired
    private ParamDao paramDao;
    private AbsGuavaCache<String, Param> cache;
    private AbsGuavaCache<String, List<Param>> parentCache;

    public ParamMgr() {
        cache = new AbsGuavaCache<String, Param>(ParamConstant.CACHE_MAX_SIZE, ParamConstant.CACHE_EXPIRE) {
            @Override
            protected Param loadData(String key) {
                Map<String, Param> paramMap = new HashedMap();
                paramDao.findAll().forEach(param -> paramMap.put(param.getParentName() + "_" + param.getName(), param));
                return paramMap.get(key);
            }
        };

        parentCache = new AbsGuavaCache<String, List<Param>>(ParamConstant.CACHE_MAX_SIZE, ParamConstant.CACHE_EXPIRE) {
            @Override
            protected List<Param> loadData(String key) {
                return paramDao.findByParentName(key);
            }
        };
    }

    public Param getByParentNameAndName(String parentName, String name) {
        return cache.get(parentName + "_" + name);
    }

    public List<Param> getByParentName(String parentName) {
        return parentCache.get(parentName);
    }

    public Param create(Param param) {
        return paramDao.saveAndFlush(param);
    }

    public Param update(Param param) {
        return paramDao.saveAndFlush(param);
    }

    public void delete(String id) {
        paramDao.delete(id);
    }

}
