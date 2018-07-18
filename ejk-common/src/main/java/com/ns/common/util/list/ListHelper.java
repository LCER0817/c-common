package com.ns.common.util.list;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuezhucao on 16/3/22.
 */
public abstract class ListHelper {
    public static <U, V> List<V> getList(List<U> list, ListCvt<U, V> cvt) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>(0);
        }
        List<V> result = new ArrayList<>(list.size());
        for (U o : list) {
            result.add(cvt.getObj(o));
        }
        return result;
    }
}
