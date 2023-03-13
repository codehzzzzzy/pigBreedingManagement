package com.hzzzzzy.project.model.dto.PigInventory;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * 导出猪只进出库Excel实体类
 *
 * @author hzzzzzy
 */
@Data
public class PigInventoryExcel{
    /**
     * 进出记录id
     */
    @ExcelProperty("进出记录id")
    private Integer id;

    /**
     * 猪只id
     */
    @ExcelProperty("猪只id")
    private Integer pigId;

    /**
     * 进入猪舍id
     */
    @ExcelProperty("进入猪舍id")
    private Integer hogringId;

    /**
     * 进入时间
     */
    @ExcelProperty("进入时间")
    private LocalDate inTime;

    /**
     * 出库时间
     */
    @ExcelProperty("出库时间")
    private LocalDate outTime;

    /**
     * 猪只来源
     */
    @ExcelProperty("猪只来源")
    private String source;

    /**
     * 猪只去向
     */
    @ExcelProperty("猪只去向")
    private String destination;
}