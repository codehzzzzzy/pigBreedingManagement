package com.hzzzzzy.project.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 饲料信息视图
 *
 * @author hzzzzzy
 */
@Data
public class FeedVO {
    /**
     * 饲料id
     */
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
}