package com.ns.common.util.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ns.common.util.exception.sys.NSException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by  liqiuwei on 2017/8/11.
 */
public abstract class AbsGuavaCache<K, V> {

    private LoadingCache<K, V> cache;

    public AbsGuavaCache(int maxSize, int expire) {
        cache = CacheBuilder.newBuilder().maximumSize(maxSize)
                .expireAfterWrite(expire, TimeUnit.SECONDS)
                .build(new CacheLoader<K, V>() {
                    @Override
                    public V load(K key) throws Exception {
                        return loadData(key);
                    }
                });
    }

    public LoadingCache<K, V> getCache() {
        return cache;
    }

    public V get(K key) throws NSException {
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            return null;
        }
    }

    protected abstract V loadData(K key);

}
