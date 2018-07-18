package com.ns.common.util.map;

import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuezhucao on 16/3/22.
 */
public abstract class MapHelper {
    public static <K, V> Map<K, V> getMap(List<V> list, ListMapCvt<K, V> cvt) {
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>(0);
        }
        Map<K, V> result = new HashMap<>(list.size());
        for (V o : list) {
            result.put(cvt.getKey(o), o);
        }
        return result;
    }
}
