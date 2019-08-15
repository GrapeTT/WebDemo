package com.demo.base.impl;

import com.alibaba.fastjson.JSONObject;
import com.demo.base.BaseDao;
import com.demo.base.BaseService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Name：BaseServiceImpl
 * @Package：com.cjpb.base.impl
 * @Descripition：T：对象，K：对象的Example
 * @Author：涛
 * @Date：2018/9/4 14:55
 * @Version：1.0
 */
public abstract class BaseServiceImpl<T, K> implements BaseService<T> {
    public static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * @Description：获得dao层对象
     * @Author：涛
     * @Date：2018/9/5 9:18
     */
    public abstract BaseDao<T, K> getDao();
    
    /**
     * @Description：获得对象的Example
     * @Author：涛
     * @Date：2018/9/4 17:04
     */
    public abstract K getExample(T t);
    
    @Override
    public Integer insertEntry(T t) {
        int res;
        try {
            res = getDao().insert(t);
        } catch (Exception e) {
            LOGGER.error("添加对象失败！Object=" + JSONObject.toJSONString(t), e);
            return 0;
        }
        return res;
    }
    
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        int res;
        try {
            res = getDao().deleteByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("通过主键（id）删除对象失败！id=" + id, e);
            return 0;
        }
        return res;
    }
    
    @Override
    public Integer deleteByCondition(T t) {
        int res;
        try {
            res = getDao().deleteByExample(getExample(t));
        } catch (Exception e) {
            LOGGER.error("通过条件删除对象失败！Object=" + JSONObject.toJSONString(t), e);
            return 0;
        }
        return res;
    }
    
    @Override
    public Integer updateByPrimaryKey(T t) {
        int res;
        try {
            res = getDao().updateByPrimaryKey(t);
        } catch (Exception e) {
            LOGGER.error("通过主键（id）更新对象失败！Object=" + JSONObject.toJSONString(t), e);
            return 0;
        }
        return res;
    }
    
    @Override
    public T selectByPrimaryKey(Long id) {
        T t = null;
        try {
            t = getDao().selectByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("通过主键（id）查询对象失败！id=" + id, e);
            return null;
        }
        return t;
    }
    
    @Override
    public List<T> selectListByCondition(T t) {
        List<T> ts = null;
        try {
            ts = getDao().selectByExample(getExample(t));
            if(CollectionUtils.isEmpty(ts)) {
                ts = null;
            }
        } catch (Exception e) {
            LOGGER.error("通过条件查询对象失败！Object=" + JSONObject.toJSONString(t), e);
            return null;
        }
        return ts;
    }
    
    @Override
    public T selectOneByCondition(T t) {
        List<T> ts = selectListByCondition(t);
        if(CollectionUtils.isNotEmpty(ts)) {
            return ts.get(0);
        }
        return null;
    }
    
    @Override
    public PageInfo<T> selectPage(T t) {
        PageInfo<T> page = new PageInfo<>();
        try {
            List<T> ts = getDao().selectByExample(getExample(t));
            if (CollectionUtils.isNotEmpty(ts)) {
                page.setSize(ts.size());
                page.setList(ts);
            }
        } catch (Exception e) {
            LOGGER.error("通过条件分页查询失败！Object=" + JSONObject.toJSONString(t), e);
            return null;
        }
        return page;
    }
    
    @Override
    public Long selectCountByConditon(T t) {
        long count = 0;
        try {
            count = getDao().countByExample(getExample(t));
        } catch (Exception e) {
            LOGGER.error("通过条件查询记录数量失败！Object=" + JSONObject.toJSONString(t), e);
            return null;
        }
        return count;
    }
}
