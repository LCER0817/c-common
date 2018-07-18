package com.ns.common.util.bean;

import com.ns.common.util.exception.sys.NSException;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuezhucao on 2017/1/28.
 */
public interface IVoUtil<T> {
    T getVo(T t) throws NSException;

    default List<T> getVos(List<T> beans) throws NSException {
        if (CollectionUtils.isEmpty(beans)) {
            return beans;
        }
        List<T> result = new ArrayList<>(beans.size());
        for (T bean : beans) {
            result.add(getVo(bean));
        }
        return result;
    }
}
