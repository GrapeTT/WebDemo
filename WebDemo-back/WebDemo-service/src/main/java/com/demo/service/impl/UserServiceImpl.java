package com.demo.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.entity.User;
import com.demo.dao.mapper.UserMapper;
import com.demo.service.UserService;
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
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(condition);
        User user = userMapper.selectOne(queryWrapper);
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
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(condition);
        return userMapper.selectOne(queryWrapper);
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
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(condition);
        return userMapper.selectOne(queryWrapper);
    }
}
