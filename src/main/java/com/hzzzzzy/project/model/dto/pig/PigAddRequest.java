package com.hzzzzzy.project.model.dto.pig;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 肉猪添加信息请求
 *
 * @author hzzzzzy
 */
@Data
public class PigAddRequest {
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

}
