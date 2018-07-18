package com.ns.common.dao.spi.redis;

import com.ns.common.util.gson.GsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuezhucao on 16/7/1.
 */
public class AbsNSRedisDao {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    protected boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    protected Set<String> keys(String keyPattern) {
        if (StringUtils.isEmpty(keyPattern)) {
            return Collections.emptySet();
        }
        return redisTemplate.keys(keyPattern);
    }


    protected String get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    protected Long getLong(String key) {
        String value = get(key);
        if (value != null) {
            return Long.valueOf(value);
        }
        return null;
    }

    protected <T> T getObj(String key, Class<T> classOfT) {
        String value = get(key);
        if (value != null) {
            return GsonUtil.fromJson(value, classOfT);
        }
        return null;
    }

    protected void set(String key, String value) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(value)) {
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }

    protected void setLong(String key, long value) {
        set(key, String.valueOf(value));
    }

    protected <T> void setObj(String key, T obj) {
        if (StringUtils.isEmpty(key)
                || obj == null) {
            return;
        }
        String value = GsonUtil.toJson(obj);
        set(key, value);
        return;
    }

    protected List<String> mget(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        return redisTemplate.opsForValue().multiGet(keys);
    }

    protected Map<String, String> getMap(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }

        List<String> values = redisTemplate.opsForValue().multiGet(keys);

        if (CollectionUtils.isEmpty(values)
                || keys.size() != values.size()) {
            return null;
        }

        Map<String, String> result = new HashMap<>(keys.size());

        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), values.get(i));
        }

        return result;
    }

    protected void hset(String key, String field, String value) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(field)) {
            return;
        }
        redisTemplate.opsForHash().put(key, field, value);
    }

    protected Set<String> hkeys(String key) {
        if (StringUtils.isEmpty(key)) {
            return new HashSet<String>(0);
        }
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        return ops.keys(key);
    }

    protected String hget(String key, String field) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            return null;
        }
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        return ops.get(key, field);
    }

    protected Map<String, String> hgetAll(String key) {
        if (StringUtils.isEmpty(key)) {
            return new HashMap<String, String>(0);
        }
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        return ops.entries(key);
    }

    protected boolean setnx(String key, String value) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(value)) {
            return false;
        }
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        return false;
    }

    protected Long incr(String key) {
        return incrBy(key, 1);
    }

    protected Long incr(String key, long value) {
        if (value < 0) {
            value = -value;
        }
        return incrBy(key, value);
    }

    protected Long decr(String key) {
        return incrBy(key, -1);
    }

    protected Long decr(String key, long value) {
        if (value < 0) {
            value = -value;
        }
        return incrBy(key, -value);
    }

    private Long incrBy(String key, long value) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForValue().increment(key, value);
    }

    protected void delete(String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        redisTemplate.delete(key);
    }

    protected void hDelete(String key, String field) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(field)) {
            return;
        }
        redisTemplate.opsForHash().delete(key, field);
    }

    protected Long hincr(String key, String field, long value) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(field)
                || value < 0) {
            return null;
        }
        return redisTemplate.opsForHash().increment(key, field, value);
    }

    protected Long hdecr(String key, String field, long value) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(field)
                || value < 0) {
            return null;
        }
        return redisTemplate.opsForHash().increment(key, field, -value);
    }

    protected void expire(String key, long expire, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key)
                || expire < 0) {
            return;
        }
        redisTemplate.expire(key, expire, timeUnit);
    }

    /**
     * 获取剩余过期时间
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    protected void defer(String key, int expire, TimeUnit timeUnit) {
        long ttl = redisTemplate.getExpire(key, timeUnit);
        if (ttl > -2 && expire > ttl) {
            expire(key, expire, timeUnit);
        }
    }

    protected Long zremRange(String key, double start, double end) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForZSet().removeRangeByScore(key, start, end);
    }

    protected Set<String> zrange(String key, long start, long end) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    protected Boolean zadd(String key, String member, long score) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForZSet().add(key, member, Double.valueOf(score));
    }

}
