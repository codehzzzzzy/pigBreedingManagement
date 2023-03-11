package com.hzzzzzy.project.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 猪舍视图
 */
@Data
public class HoringVO {
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
}
