package com.demo.dao.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * Base domain
 * </p>
 *
 * @author tao
 * @since 2021-08-03
 */
public class BaseEntity implements Serializable {
    /**
     * 主键ID（mybatis-plus给的主键ID会从很大一个数开始，把Type设置为自动，这样mybatis-plus就会忽略掉主键，转而用数据库自己的自增长模式）
     */
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}
