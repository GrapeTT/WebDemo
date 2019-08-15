package com.demo.service.impl;

import com.demo.base.BaseDao;
import com.demo.base.impl.BaseServiceImpl;
import com.demo.dao.UserDao;
import com.demo.domain.User;
import com.demo.domain.UserExample;
import com.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author：涛
 * @Descripition：用户服务实现类
 * @Date：2018/3/23 16:44
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserExample> implements UserService {
    @Resource
    private UserDao userDao;
    
    @Override
    public BaseDao<User, UserExample> getDao() {
        return userDao;
    }
    
    @Override
    public UserExample getExample(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(user.getId() != null) {
            criteria.andIdEqualTo(user.getId());
        }
        if(user.getUid() != null) {
            criteria.andUidEqualTo(user.getUid());
        }
        if(user.getUsername() != null) {
            criteria.andUsernameEqualTo(user.getUsername());
        }
        if(user.getPassword() != null) {
            criteria.andPasswordEqualTo(user.getPassword());
        }
        if(user.getCreateTime() != null) {
            criteria.andCreateTimeEqualTo(user.getCreateTime());
        }
        if(user.getUpdateTime() != null) {
            criteria.andUpdateTimeEqualTo(user.getUpdateTime());
        }
        userExample.setOrderByClause("id asc");
        return userExample;
    }
    
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
        User user = selectOneByCondition(condition);
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
        User user = new User();
        user.setUid(uid);
        return selectOneByCondition(user);
    }
    
    /**
     * @param username
     * @Description：通过uid获取用户
     * @Author：涛哥
     * @Time：2019/4/19 0:57
     */
    @Override
    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return selectOneByCondition(user);
    }
}
