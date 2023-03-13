package com.hzzzzzy.project.model.dto.PigInventory;


import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 肉猪进出库批量添加信息 辅助类
 *
 * @author hzzzzzy
 */
@Data
public class PigInventoryAddBatchAuxiliary {

    /**
     * 进入猪舍id
     */
    private Integer hogringId;

    /**
     * 进入时间
     */
    private LocalDate inTime;

    /**
     * 出库时间
     */
    private LocalDate outTime;

    /**
     * 猪只来源
     */
    private String source;

    /**
     * 猪只去向
     */
    private String destination;
}
