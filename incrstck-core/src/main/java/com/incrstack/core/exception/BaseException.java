package com.incrstack.core.exception;

import com.incrstack.core.http.ErrorEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 自定义异常基类
 *
 * @author Hakil
 * @date 2021/11/25 17:50
 */

public class BaseException extends RuntimeException implements ErrorEntity {

    private final String errorCode;
    private final String errorMessage;
    @Getter
    @Setter
    private int httpStatus = 500;
    @Setter
    private List<ErrorEntity> errorEntities;

    public BaseException() {
        super("[" + ErrorEntity.DEFAULT_ERROR_CODE + "]" + ErrorEntity.DEFAULT_ERROR_MESSAGE);
        this.errorCode = ErrorEntity.DEFAULT_ERROR_CODE;
        this.errorMessage = DEFAULT_ERROR_MESSAGE;
        this.errorEntities = Collections.emptyList();
    }

    public BaseException(String errorCode) {
        super("[" + errorCode + "]" + ErrorEntity.DEFAULT_ERROR_MESSAGE);
        this.errorCode = errorCode;
        this.errorMessage = DEFAULT_ERROR_MESSAGE;
        this.errorEntities = Collections.emptyList();
    }

    public BaseException(String errorCode, String errorMessage) {
        super("[" + errorCode + "]" + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorEntities = Collections.emptyList();
    }

    public BaseException(String errorCode, String errorMessage, BaseException cause) {
        super("[" + errorCode + "]" + errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        addCase(cause);
    }

    public BaseException(String errorCode, BaseException cause) {
        super("[" + errorCode + "]" + ErrorEntity.DEFAULT_ERROR_MESSAGE, cause);
        this.errorCode = errorCode;
        this.errorMessage = DEFAULT_ERROR_MESSAGE;
        addCase(cause);
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public ErrorEntity[] getDetails() {
        return this.errorEntities.toArray(new ErrorEntity[0]);
    }

    private void addCase(BaseException cause) {
        if (cause.getDetails().length == 0) {
            this.errorEntities = Collections.singletonList(new ErrorEntityAdapter(cause));
        } else {
            this.errorEntities = new ArrayList<>(Arrays.asList(cause.getDetails()));
            cause.setErrorEntities(Collections.emptyList());
            this.errorEntities.add(new ErrorEntityAdapter(cause));
        }
    }

    /**
     * 错误信息输出适配器
     * 主要作用是区别异常和错误返回的toString()格式化输出
     * 例如.使用printStackTrace()打印异常信息时使用默认异常打印格式
     * 而在作为ErrorEntity对象toString()时打印Json格式
     */
    private static class ErrorEntityAdapter implements ErrorEntity {
        private final ErrorEntity errorEntity;

        public ErrorEntityAdapter(BaseException cause) {
            errorEntity = cause;
        }

        @Override
        public String getErrorCode() {
            return errorEntity.getErrorCode();
        }

        @Override
        public String getErrorMessage() {
            return errorEntity.getErrorMessage();
        }

        @Override
        public ErrorEntity[] getDetails() {
            return errorEntity.getDetails();
        }

        @Override
        public String toString() {
            return "{\"errorCode\":\"" + this.getErrorCode() + "\",\"message\":\"" + this.getErrorMessage() + "\"}";
        }
    }
}
