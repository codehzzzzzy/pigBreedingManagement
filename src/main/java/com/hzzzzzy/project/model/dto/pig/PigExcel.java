package com.hzzzzzy.project.model.dto.pig;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 导出肉猪Excel的实体类
 *
 * @author hzzzzzy
 */
@Data
public class PigExcel {

    /**
     * 肉猪id
     */
    @ExcelProperty("肉猪id")
    private Integer id;

    /**
     * 肉猪品种
     */
    @ExcelProperty("肉猪品种")
    private String breed;

    /**
     * 肉猪年龄
     */
    @ExcelProperty("肉猪年龄")
    private Integer age;

    /**
     * 肉猪性别
     */
    @ExcelProperty("肉猪性别")
    private Integer gender;

    /**
     * 肉猪体重
     */
    @ExcelProperty("肉猪体重")
    private BigDecimal weight;

    /**
     * 肉猪健康状态(0-不健康;1-亚健康;2-健康)
     */
    @ExcelProperty("肉猪健康状态")
    private Integer health;

    /**
     * 肉猪饲料种类
     */
    @ExcelProperty("肉猪饲料种类")
    private String feedType;

    /**
     * 肉猪是否出库(0-未出库;1-已出库)
     */
    @ExcelProperty("肉猪是否出库")
    private Integer status;

    /**
     * 肉猪所在猪舍id
     */
    @ExcelProperty("肉猪所在猪舍id")
    private Integer hogringId;
}