package com.demo.service;

import com.demo.base.BaseService;
import com.demo.domain.User;

import java.util.List;

/**
 * @Author：涛
 * @Descripition：用户服务类
 * @Date：2018/3/23 16:42
 */
public interface UserService extends BaseService<User> {
    /**
     * @Description：判重
     * @Author：涛哥
     * @Time：2019/4/13 15:40
     */
    Boolean isRepeat(String username);
    
    /**
     * @Description：通过uid获取用户
     * @Author：涛哥
     * @Time：2019/4/19 0:57
     */
    User getUserByUid(String uid);
    
    /**
     * @Description：通过uid获取用户
     * @Author：涛哥
     * @Time：2019/4/19 0:57
     */
    User getUserByUsername(String username);
}
