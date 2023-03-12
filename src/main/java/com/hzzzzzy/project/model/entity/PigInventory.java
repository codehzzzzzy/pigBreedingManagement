package com.hzzzzzy.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 猪只进出库管理
 * @TableName pig_inventory
 */
@TableName(value ="pig_inventory")
@Data
public class PigInventory implements Serializable {
    /**
     * 进出记录id 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 猪只id
     */
    private Integer pigId;

    /**
     * 进入猪舍id
     */
    private Integer hogringId;

    /**
     * 进入时间
     */
    private Date inTime;

    /**
     * 出库时间
     */
    private Date outTime;

    /**
     * 猪只来源
     */
    private String source;

    /**
     * 猪只去向
     */
    private String destination;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}