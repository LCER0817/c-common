package com.ns.common.dao.spi.jpa;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by xuezhucao on 2017/6/5.
 */
@NoRepositoryBean
public interface JpaDao<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
    default T getAndLock(EntityManager em, Class<T> clazz, ID id) {
        return em.find(clazz, (Object) id, LockModeType.PESSIMISTIC_WRITE);
    }

    /**
     * 强制执行持久化
     *
     * @param entity
     * @return
     */
    T saveAndFlush(T entity);

    default List<T> findByIds(List<ID> ids) {
        return IteratorUtils.toList(findAll(ids).iterator());
    }

    default List<T> saves(List<T> objs) {
        return IteratorUtils.toList(save(objs).iterator());
    }
}
