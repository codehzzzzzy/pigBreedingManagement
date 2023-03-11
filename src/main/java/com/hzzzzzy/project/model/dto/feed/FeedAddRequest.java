package com.hzzzzzy.project.model.dto.feed;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 饲料添加信息请求
 *
 * @author hzzzzzy
 */
@Data
public class FeedAddRequest {
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
}
