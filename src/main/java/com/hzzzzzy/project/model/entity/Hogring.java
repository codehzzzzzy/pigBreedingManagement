package com.hzzzzzy.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 猪舍
 * @TableName hogring
 */
@TableName(value ="hogring")
@Data
public class Hogring implements Serializable {
    /**
     * 猪舍id 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer hogringId;

    /**
     * 猪舍面积
     */
    private BigDecimal hogringArea;

    /**
     * 猪舍容量
     */
    private Integer hogringCapacity;

    /**
     * 猪舍状态
     */
    private Integer hogringStatus;

    /**
     * 管理人员id
     */
    private Long userId;

    /**
     * 猪只id
     */
    private String pigId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}