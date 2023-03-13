package com.hzzzzzy.project.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 肉猪进出库查询详细信息的视图
 *
 * @author hzzzzzy
 */
@Data
public class PigInventoryGetOneVO{
    /**
     * 进出记录id
     */
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
    private LocalDate inTime;

    /**
     * 出库时间
     */
    private LocalDate outTime;

    /**
     * 猪只来源
     */
    private String source;

    /**
     * 猪只去向
     */
    private String destination;

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
}