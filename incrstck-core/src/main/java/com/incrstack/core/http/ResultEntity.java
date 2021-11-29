package com.incrstack.core.http;

import java.io.Serializable;

/**
 * 数据返回接口
 *
 * @author Hakil
 * @date 2020/7/22 19:43
 */
@FunctionalInterface
public interface ResultEntity<T> extends Serializable {

    /**
     * 请求资源状态
     * 一般用于映射到http外部status
     *
     * @return 资源状态非业务状态
     */
    default int getHttpStatus() {
        return 200;
    }

    /**
     * 获取接口返回内容
     *
     * @return 接口返回内容
     */
    T getData();
}
