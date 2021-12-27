package com.incrstack.security.web.access;

import java.util.Map;

/**
 * @author Hakil
 * @date 2021/12/27 17:11
 */
public interface MetadataSourceManager<K, V> {

    public void add(K K, V v);

    public V get(K K);

    public Map<K, V> getAll();

    public void delete(K k);

    public void deleteAll();

    public void update(K k, V v);
}
