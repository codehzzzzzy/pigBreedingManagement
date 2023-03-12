package com.hzzzzzy.project.model.dto.PigInventory;


import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 肉猪进出库批量添加信息请求
 *
 * @author hzzzzzy
 */
@Data
public class PigInventoryAddBatchRequest {

    /**
     * 猪只id
     */
    private List<Integer> pigIdList;

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