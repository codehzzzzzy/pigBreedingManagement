package com.hzzzzzy.project.model.dto.hogring;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 导出猪舍Excel的实体类
 *
 * @author hzzzzzy
 */
@Data
public class HogringExcel {

    /**
     * 猪舍id
     */
    @ExcelProperty("猪舍id")
    private Integer hogringId;

    /**
     * 猪舍面积
     */
    @ExcelProperty("猪舍面积")
    private BigDecimal hogringArea;

    /**
     * 猪舍容量
     */
    @ExcelProperty("猪舍容量")
    private Integer hogringCapacity;

    /**
     * 猪舍状态
     */
    @ExcelProperty("猪舍状态")
    private Integer hogringStatus;

    /**
     * 管理人员id
     */
    @ExcelProperty("管理人员id")
    private Long userId;

    /**
     * 猪只id
     */
    @ExcelProperty("猪只id")
    private String pigId;
}