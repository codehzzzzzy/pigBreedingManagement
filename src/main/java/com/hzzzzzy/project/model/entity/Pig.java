package com.hzzzzzy.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 猪只信息
 * @TableName pig
 */
@TableName(value ="pig")
@Data
public class Pig implements Serializable {
    /**
     * 猪只id 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 品种
     */
    private String breed;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 体重
     */
    private BigDecimal weight;

    /**
     * 健康状态(0-不健康;1-亚健康;2-健康)
     */
    private Integer health;

    /**
     * 饲料种类
     */
    private String feedType;

    /**
     * 是否出库(0-未出库;1-已出库)
     */
    private Integer status;

    /**
     * 所在猪舍id
     */
    private Integer hogringId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}