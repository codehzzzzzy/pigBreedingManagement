package com.hzzzzzy.project.model.dto.hogring;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 猪舍更新信息请求
 *
 * @author hzzzzzy
 */
@Data
public class HogringUpdateRequest {

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
     * 猪只id
     */
    private List<Integer> pigId;
}
