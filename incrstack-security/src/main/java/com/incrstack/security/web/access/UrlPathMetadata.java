package com.incrstack.security.web.access;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * URL路径
 *
 * @author Hakil
 * @date 2021/12/27 21:44
 */
public interface UrlPathMetadata {

    String getPattern();

    HttpMethod getMethod();

    RequestMatcher getRequestMatcher();
}
