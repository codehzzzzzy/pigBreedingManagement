package com.hzzzzzy.project.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 肉猪进出库查询信息的视图
 *
 * @author hzzzzzy
 */
@Data
public class PigInventoryVO {
    /**
     * 进出记录id
     */
    private Integer id;

    /**
     * 猪只id
     */
    private Integer pigId;

    /**
     * 进入时间
     */
    private Date inTime;

    /**
     * 出库时间
     */
    private Date outTime;

    /**
     * 猪只来源
     */
    private String source;

    /**
     * 猪只去向
     */
    private String destination;
}