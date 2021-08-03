package com.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.domain.User;
import com.demo.dao.mapper.UserMapper;
import com.demo.service.UserService;
import cn.hutool.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author tao
 * @since 2021-01-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Log LOG = Log.get();
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * @param username
     * @Description：判重
     * @Author：涛哥
     * @Time：2019/4/13 15:40
     */
    @Override
    public Boolean isRepeat(String username) {
        User condition = new User();
        condition.setUsername(username);
        User user = userMapper.selectOne(Wrappers.query(condition));
        if(user == null) {
            return false;
        }
        return true;
    }
    
    /**
     * @param uid
     * @Description：通过uid获取用户
     * @Author：涛哥
     * @Time：2019/4/19 0:57
     */
    @Override
    public User getUserByUid(String uid) {
        User condition = new User();
        condition.setUid(uid);
        return userMapper.selectOne(Wrappers.query(condition));
    }
    
    /**
     * @param username
     * @Description：通过uid获取用户
     * @Author：涛哥
     * @Time：2019/4/19 0:57
     */
    @Override
    public User getUserByUsername(String username) {
        User condition = new User();
        condition.setUsername(username);
        return userMapper.selectOne(Wrappers.query(condition));
    }
}
