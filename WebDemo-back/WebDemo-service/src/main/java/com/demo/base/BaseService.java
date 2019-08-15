package com.demo.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Name：BaseService
 * @Package：com.cjpb.base
 * @Descripition：T：对象
 * @Author：涛
 * @Date：2018/9/4 14:54
 * @Version：1.0
 */
public interface BaseService<T> {
    /**
     * @Description：添加对象
     * @Author：涛
     * @Date：2018/9/4 16:40
     */
    Integer insertEntry(T t);
    
    /**
     * @Description：通过主键（id）删除对象
     * @Author：涛
     * @Date：2018/9/4 16:40
     */
    Integer deleteByPrimaryKey(Long id);
    
    /**
     * @Description：通过条件删除对象
     * @Author：涛
     * @Date：2018/9/4 16:40
     */
    Integer deleteByCondition(T t);
    
    /**
     * @Description：通过主键（id）更新对象
     * @Author：涛
     * @Date：2018/9/4 16:40
     */
    Integer updateByPrimaryKey(T t);
    
    /**
     * @Description：通过主键（id）查询对象
     * @Author：涛
     * @Date：2018/9/4 16:47
     */
    T selectByPrimaryKey(Long id);
    
    /**
     * @Description：通过条件查询对象
     * @Author：涛
     * @Date：2018/9/4 16:48
     */
    List<T> selectListByCondition(T t);
    
    /**
     * @Description：通过条件查询并返回一个对象
     * @Author：涛哥
     * @Time：2019/4/10 19:34
     */
    T selectOneByCondition(T t);
    
    /**
     * @Description：通过条件分页查询
     * @Author：涛
     * @Date：2018/9/4 16:49
     */
    PageInfo<T> selectPage(T t);
    
    /**
     * @Description：通过条件查询记录数量
     * @Author：涛
     * @Date：2018/9/4 16:50
     */
    Long selectCountByConditon(T t);
}
