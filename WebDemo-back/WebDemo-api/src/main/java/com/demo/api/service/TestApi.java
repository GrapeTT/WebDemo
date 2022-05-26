package com.demo.api.service;

import com.demo.api.model.Message;

/**
 * @Name：Message
 * @Package：com.demo.api.service
 * @Descripition：测试api
 * @Author：涛
 * @Date：2022/5/26 11:24
 * @Version：1.0
 */
public interface TestApi {
    /**
     * 测试
     * @author 涛
     * @date 2022/5/26 11:24
     */
    Message<Void> test();
}
