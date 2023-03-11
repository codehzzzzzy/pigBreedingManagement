package com.hzzzzzy.project.model.dto.feed;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 饲料更新信息请求
 *
 * @author hzzzzzy
 */
@Data
public class FeedUpdateRequest {
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
