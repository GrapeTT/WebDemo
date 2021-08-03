package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.dao.domain.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author tao
 * @since 2021-01-05
 */
public interface UserService extends IService<User> {
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
