package com.incrstack.core.http;

/**
 * 错误信息返回接口
 *
 * @author Hakil
 * @date 2020/7/25 19:46
 */
public interface ErrorEntity {

    public static String DEFAULT_ERROR_CODE = "INTERNAL_SERVER_ERROR";
    public static String DEFAULT_ERROR_MESSAGE = "内部错误";

    /**
     * 业务执行错误代码
     *
     * @return 错误代码
     */
    default String getErrorCode() {
        return DEFAULT_ERROR_CODE;
    }

    /**
     * 描述业务错误信息
     *
     * @return 错误消息
     */
    default String getErrorMessage() {
        return DEFAULT_ERROR_MESSAGE;
    }

    /**
     * 更详细的错误信息
     *
     * @return 更多错误信息
     */
    default ErrorEntity[] getDetails() {
        return new ErrorEntity[]{};
    }
}
