package com.hzzzzzy.project.model.dto.PigInventory;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 肉猪进出库添加信息请求
 *
 * @author hzzzzzy
 */
@Data
public class PigInventoryAddRequest {

    /**
     * 猪只id
     */
    private Integer pigId;

    /**
     * 进入猪舍id
     */
    private Integer hogringId;

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
