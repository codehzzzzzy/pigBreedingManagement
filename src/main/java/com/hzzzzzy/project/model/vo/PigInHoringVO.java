package com.hzzzzzy.project.model.vo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 查询猪舍内所有肉猪的视图
 */
@Data
public class PigInHoringVO {
    /**
     * 猪只id 主键
     */
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
     * 是否出库(0-未出库;1-已出库)
     */
    private Integer status;
}
