package com.demo.dao;

import com.demo.base.BaseDao;
import com.demo.domain.User;
import com.demo.domain.UserExample;
import org.apache.ibatis.annotations.Mapper;

public interface UserDao extends BaseDao<User, UserExample> {
}