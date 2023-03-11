package com.hzzzzzy.project.model.dto.hogring;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * 猪舍添加信息请求
 *
 * @author hzzzzzy
 */
@Data
public class HogringAddRequest {

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

    /**
     * 猪只id
     */
    private List<Integer> pigId;
}
