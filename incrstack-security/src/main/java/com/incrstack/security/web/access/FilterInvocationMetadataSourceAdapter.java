package com.incrstack.security.web.access;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态可变的权限映射元数据
 *
 * @author Hakil
 * @date 2021/11/25 17:54
 */
@Slf4j
public class FilterInvocationMetadataSourceAdapter implements SecurityMetadataSource, MetadataSourceManager<RequestMatcher, Collection<ConfigAttribute>> {

    protected final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
    /**
     * 默认配置文件或者注解等配置的元数据
     */
    protected final FilterInvocationSecurityMetadataSource defaultMetadataSource;

    public FilterInvocationMetadataSourceAdapter(FilterInvocationSecurityMetadataSource defaultMetadataSource) {
        this.defaultMetadataSource = defaultMetadataSource;
        requestMap = new ConcurrentHashMap<>();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        int count = 0;
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : this.requestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Did not match request to {} - {} ({}/{})", entry.getKey(),
                            entry.getValue(), ++count, this.requestMap.size());
                }
            }
        }
        return defaultMetadataSource.getAttributes(object);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        this.requestMap.values().forEach(allAttributes::addAll);
        allAttributes.addAll(defaultMetadataSource.getAllConfigAttributes());
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public void add(RequestMatcher requestMatcher, Collection<ConfigAttribute> configAttributes) {
        requestMap.put(requestMatcher, configAttributes);
    }

    @Override
    public Collection<ConfigAttribute> get(RequestMatcher requestMatcher) {
        return requestMap.get(requestMatcher);
    }

    @Override
    public Map<RequestMatcher, Collection<ConfigAttribute>> getAll() {
        return requestMap;
    }

    @Override
    public void delete(RequestMatcher requestMatcher) {
        requestMap.remove(requestMatcher);
    }

    @Override
    public void deleteAll() {
        requestMap.clear();
    }

    @Override
    public void update(RequestMatcher requestMatcher, Collection<ConfigAttribute> configAttributes) {
        requestMap.put(requestMatcher, configAttributes);
    }
}
