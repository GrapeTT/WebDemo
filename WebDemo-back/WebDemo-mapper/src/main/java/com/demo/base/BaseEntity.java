/*
 * Copyright (c) 2015 www.jd.com All rights reserved.
 * 本软件源代码版权归京东智能集团所有,未经许可不得任意复制与传播.
 */
package com.demo.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <b>描述：</b> Base entity<br/>
 *
 * @author <b>作者：</b> wutao91@jd.com<br/>
 * @date <b>时间：</b> 2021/1/5 20:25<br/>
 * <b>Copyright (c)</b> 2015-2021京东智能-版权所有<br/>
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 主键ID（mybatis-plus给的主键ID会从很大一个数开始，把Type设置为自动，这样mybatis-plus就会忽略掉主键，转而用数据库自己的自增长模式）
     */
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;
}
