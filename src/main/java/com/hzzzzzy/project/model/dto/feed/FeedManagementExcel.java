package com.hzzzzzy.project.model.dto.feed;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 导出饲料Excel的实体类
 */
@Data
public class FeedManagementExcel {
    /**
     * 饲料id
     */
    @ExcelProperty("饲料id")
    private Integer id;

    /**
     * 饲料种类
     */
    @ExcelProperty("饲料种类")
    private String feedType;

    /**
     * 使用猪舍id
     */
    @ExcelProperty("使用猪舍id")
    private Integer hogringId;

    /**
     * 饲料用量
     */
    @ExcelProperty("饲料用量")
    private BigDecimal feedAmount;

    /**
     * 管理人员id
     */
    @ExcelProperty("管理人员id")
    private Long feeder;

}