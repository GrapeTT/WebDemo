package com.demo.service.api;

import cn.hutool.log.Log;
import com.demo.api.model.Message;
import com.demo.api.service.TestApi;
import org.springframework.stereotype.Service;

/**
 * @Name：Message
 * @Package：com.demo.service.api
 * @Descripition：测试api
 * @Author：涛
 * @Date：2022/5/26 11:24
 * @Version：1.0
 */
@Service
public class TestApiImpl implements TestApi {
    private static final Log LOG = Log.get();
    
    /**
     * 测试
     *
     * @author 涛
     * @date 2022/5/26 11:24
     */
    @Override
    public Message<Void> test() {
        return null;
    }
}
