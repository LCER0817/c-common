package com.ns.common.util.filter.spi;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by caoxuezhu01 on 14-10-24.
 */
public abstract class AbsFilter implements HandlerInterceptor {

    protected abstract String[] getIgnorePaths();

    protected abstract String[] getFilterPaths();

    protected abstract boolean checkRequest(HttpServletRequest request);

    protected abstract void handleBadRequest(HttpServletRequest request,
                                             HttpServletResponse response)
            throws IOException, ServletException;

    private boolean canIgnoreError(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String ignorePath : getIgnorePaths()) {
            if (uri.endsWith(ignorePath)) {
                return true;
            }
        }
        String contextPath = request.getContextPath();
        if (contextPath.endsWith("/")) {
            contextPath = contextPath.substring(0, contextPath.length() - 1);
        }
        for (String path : getFilterPaths()) {
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            path = contextPath + "/" + path;
            if (uri.startsWith(path)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!checkRequest(request)) {
            if (!canIgnoreError(request)) {
                handleBadRequest(request, response);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
