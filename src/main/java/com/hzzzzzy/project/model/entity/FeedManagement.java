package com.hzzzzzy.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 饲料管理
 * @TableName feed_management
 */
@TableName(value ="feed_management")
@Data
public class FeedManagement implements Serializable {
    /**
     * 饲料id 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 饲料种类
     */
    private String feedType;

    /**
     * 使用猪舍
     */
    private Integer hogringId;

    /**
     * 饲料用量
     */
    private BigDecimal feedAmount;

    /**
     * 管理人员id
     */
    private Long feeder;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}