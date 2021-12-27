package com.incrstack.security.web.access;

import java.util.Date;

/**
 * 元数据变动事件
 *
 * @author Hakil
 * @date 2021/12/27 22:47
 */
public interface MetadataEvent {

    State getState();

    Date getTimestamp();

    public static enum State {
        ADDED,
        UPDATED,
        DELETED;
    }
}
