package com.demo.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Name：BaseDao
 * @Package：com.cjpb.base
 * @Descripition：
 * @Author：涛
 * @Date：2018/9/4 21:00
 * @Version：1.0
 */
public interface BaseDao<T, K> {
    long countByExample(K example);
    
    int deleteByExample(K example);
    
    int deleteByPrimaryKey(Long id);
    
    int insert(T record);
    
    int insertSelective(T record);
    
    List<T> selectByExample(K example);
    
    T selectByPrimaryKey(Long id);
    
    int updateByExampleSelective(@Param("record") T record, @Param("example") K example);
    
    int updateByExample(@Param("record") T record, @Param("example") K example);
    
    int updateByPrimaryKeySelective(T record);
    
    int updateByPrimaryKey(T record);
}
