package com.hzzzzzy.project.service;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzzzzzy.project.model.dto.PigInventory.PigInventoryAddBatchRequest;
import com.hzzzzzy.project.model.dto.PigInventory.PigInventoryAddRequest;
import com.hzzzzzy.project.model.dto.PigInventory.PigInventoryDeleteRequest;
import com.hzzzzzy.project.model.dto.PigInventory.PigInventoryUpdateRequest;
import com.hzzzzzy.project.model.entity.PigInventory;
import com.hzzzzzy.project.model.vo.PigInventoryGetOneVO;
import com.hzzzzzy.project.model.vo.PigInventoryVO;
import org.springframework.web.bind.annotation.PathVariable;

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


    /**
     * 肉猪进出库管理员 更新 肉猪进出信息
     *
     * @param pigInventoryUpdateRequest
     * @param request
     * @return
     */
    boolean update(PigInventoryUpdateRequest pigInventoryUpdateRequest, HttpServletRequest request);


    /**
     * 肉猪进出库管理员 删除 肉猪进出信息
     *
     * @param pigInventoryDeleteRequest
     * @param request
     * @return
     */
    boolean delete(PigInventoryDeleteRequest pigInventoryDeleteRequest, HttpServletRequest request);

    /**
     * 获取肉猪进出的详细信息
     *
     * @param id
     * @return
     */
    PigInventoryGetOneVO getDetailById(int id);


    /**
     * 分页获取所有肉猪进出库信息列表
     *
     * @param current
     * @param size
     * @return
     */
    Page<PigInventoryVO> getAll(long current, long size);

}
