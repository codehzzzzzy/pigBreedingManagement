package com.hzzzzzy.project.service;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzzzzzy.project.model.dto.PigInventory.PigInventoryAddBatchRequest;
import com.hzzzzzy.project.model.dto.PigInventory.PigInventoryAddRequest;
import com.hzzzzzy.project.model.entity.PigInventory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author hzzzzzy
* @description 针对表【pig_inventory(猪只进出库管理)】的数据库操作Service
* @createDate 2023-03-12 13:26:03
*/
public interface PigInventoryService extends IService<PigInventory> {

    /**
     * 肉猪进出库管理员 创建 肉猪进出信息
     *
     * @param pigInventoryAddRequest
     * @param request
     * @return
     */
    Integer add(PigInventoryAddRequest pigInventoryAddRequest, HttpServletRequest request);


    /**
     * 肉猪进出库管理员 批量创建 肉猪进出信息
     *
     * @param pigInventoryAddBatchRequest
     * @param request
     * @return
     */
    List<Integer> addBatch(PigInventoryAddBatchRequest pigInventoryAddBatchRequest, HttpServletRequest request);


    /**
     * 导出excel
     *
     * @param response
     * @param sheetBuilder
     * @return
     */
    boolean export(HttpServletResponse response, ExcelWriterSheetBuilder sheetBuilder);
}
