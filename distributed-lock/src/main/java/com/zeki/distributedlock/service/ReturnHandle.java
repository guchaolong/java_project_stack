package com.zeki.distributedlock.service;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/16 22:00
 */
public interface ReturnHandle<T> {
    /**
     * 业务处理
     *
     * @return
     */
    T execute();
}
