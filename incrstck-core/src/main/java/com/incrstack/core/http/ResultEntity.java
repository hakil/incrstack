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
     * 获取接口执行状态
     *
     * @return 根据httpStatus判断业务执行状态
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
