package com.incrstack.security.web.access;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.Serializable;
import java.util.Collection;

/**
 * 元数据映射对象
 *
 * @author Hakil
 * @date 2021/11/25 17:54
 */
public interface MetadataSourceEntity extends Serializable {

    RequestMatcher getMatcher();

    Collection<ConfigAttribute> getAttribute();
}
