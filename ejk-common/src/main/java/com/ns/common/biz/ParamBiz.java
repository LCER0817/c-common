package com.ns.common.biz;

import com.ns.common.bean.Param;
import com.ns.common.mgr.ParamMgr;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.ParameterException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xuezhucao on 2017/6/8.
 */
public class ParamBiz {
    @Autowired
    private ParamMgr mgr;

    public Param getByParentNameAndName(String parentName, String name) {
        if (StringUtils.isEmpty(parentName)) {
            throw new ParameterException("parentName为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new ParameterException("name为空");
        }
        return mgr.getByParentNameAndName(parentName, name);
    }

    public List<Param> getByParentName(String parentName) {
        if (StringUtils.isEmpty(parentName)) {
            throw new ParameterException("parentName为空");
        }
        return mgr.getByParentName(parentName);
    }

    public Param create(Param param) {
        return mgr.create(param);
    }

    public Param update(Param param) {
        return mgr.update(param);
    }

    public void delete(String id) throws NSException {
        mgr.delete(id);
    }

}
