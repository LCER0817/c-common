package com.ns.common.util.bean;

import com.ns.common.bean.EjkPage;
import com.ns.common.util.constant.PageConstant;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.ParameterException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiuwei on 2017/8/7.
 */
public class PageUtil {
    /**
     * 生成分页对象
     *
     * @param ejkPage
     * @return
     */
    public static Pageable createPageable(EjkPage ejkPage) throws NSException {
        if (null == ejkPage) {
            throw new ParameterException("分页对象为空");
        }
        if (ejkPage.getPagesize() <= 0) {
            throw new ParameterException("页面大小为负");
        }
        if (ejkPage.getOffset() < 0) {
            throw new ParameterException("页码为负");
        }
        int pageSize = ejkPage.getPagesize();
        int offset = ejkPage.getOffset();
        return new PageRequest(offset / pageSize, pageSize);
    }

    /**
     * 获取返回的page对象
     *
     * @param page
     * @return
     */
    public static Map<String, Object> getPageResult(Page page) {
        Map<String, Object> result = new HashedMap();
        result.put(PageConstant.MAP_KEY_RESULT_LIST, page.getContent());
        result.put(PageConstant.MAP_KEY_TOTAL_COUNT, page.getTotalElements());
        return result;
    }

    public static Map<String, Object> getPageResult(List content, long count) {
        Map<String, Object> result = new HashedMap();
        result.put(PageConstant.MAP_KEY_RESULT_LIST, content);
        result.put(PageConstant.MAP_KEY_TOTAL_COUNT, count);
        return result;
    }

    public static <T> List<T> getContent(Map<String, Object> page, Class<T> tClass) throws NSException {
        if (page == null) {
            throw new ParameterException("分页对象为空");
        }
        return (List<T>) page.get(PageConstant.MAP_KEY_RESULT_LIST);
    }

    public static void setContent(Map<String, Object> page, List content) throws NSException {
        if (page == null) {
            throw new ParameterException("分页对象为空");
        }
        if (CollectionUtils.isEmpty(content)) {
            content = new ArrayList();
        }
        page.put(PageConstant.MAP_KEY_RESULT_LIST, content);
    }

}
