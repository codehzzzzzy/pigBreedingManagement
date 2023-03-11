package com.hzzzzzy.project.model.dto.pig;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 肉猪更新信息请求
 *
 * @author hzzzzzy
 */
@Data
public class PigUpdateRequest {

    /**
     * 肉猪id
     */
    private Integer id;


    /**
     * 年龄
     */
    private Integer age;

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


    /**
     * 所在猪舍id
     */
    private Integer hogringId;
}
