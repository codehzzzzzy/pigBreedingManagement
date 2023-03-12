package com.hzzzzzy.project.controller;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.hzzzzzy.project.common.BaseResponse;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.common.ExcelUtils;
import com.hzzzzzy.project.common.ResultUtils;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.model.dto.PigInventory.PigInventoryAddBatchRequest;
import com.hzzzzzy.project.model.dto.PigInventory.PigInventoryAddRequest;
import com.hzzzzzy.project.service.PigInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 肉猪出入库管理接口
 *
 * @author hzzzzzy
 */
@RestController
@RequestMapping("/inventory")
public class PigInventoryController {
    @Autowired
    private PigInventoryService pigInventoryService;

    /**
     * 肉猪进出库管理员 创建 肉猪进出信息
     *
     * @param pigInventoryAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Integer> add(@RequestBody PigInventoryAddRequest pigInventoryAddRequest, HttpServletRequest request) {
        if (pigInventoryAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer id = pigInventoryService.add(pigInventoryAddRequest,request);
        return ResultUtils.success(id);
    }


    /**
     * 肉猪进出库管理员 批量创建 肉猪进出信息
     *
     * @param pigInventoryAddBatchRequest
     * @param request
     * @return
     */
    @PostMapping("/addBatch")
    public BaseResponse<List<Integer>> addBatch(@RequestBody PigInventoryAddBatchRequest pigInventoryAddBatchRequest, HttpServletRequest request) {
        if (pigInventoryAddBatchRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Integer> list = pigInventoryService.addBatch(pigInventoryAddBatchRequest,request);
        return ResultUtils.success(list);
    }

    /**
     * 导出excel
     *
     * @param response
     */
    @RequestMapping("/export")
    public BaseResponse<Boolean> exportExcel(HttpServletResponse response){
        ExcelWriterSheetBuilder sheetBuilder = ExcelUtils.export(response);
        boolean flag = pigInventoryService.export(response, sheetBuilder);
        return ResultUtils.success(flag);
    }


//    /**
//     * 饲料信息管理员 更新 饲料信息
//     *
//     * @param feedUpdateRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/update")
//    public BaseResponse<Boolean> update(@RequestBody FeedUpdateRequest feedUpdateRequest, HttpServletRequest request){
//        boolean flag = feedManagementService.update(feedUpdateRequest, request);
//        return ResultUtils.success(flag);
//    }


//    /**
//     * 饲料信息管理员 删除 饲料信息
//     *
//     * @param feedDeleteRequest
//     * @return
//     */
//    @PostMapping("/delete")
//    public BaseResponse<Boolean> delete(@RequestBody FeedDeleteRequest feedDeleteRequest, HttpServletRequest request) {
//        boolean flag = feedManagementService.delete(feedDeleteRequest, request);
//        return ResultUtils.success(flag);
//    }


    /**
//     * 通过饲料id获取详细信息
//     *
//     * @param id
//     * @return
//     */
//    @GetMapping("/getById")
//    public BaseResponse<FeedGetOneVO> getById(int id){
//        FeedGetOneVO feedVO = feedManagementService.getDetailById(id);
//        return ResultUtils.success(feedVO);
//    }


//    /**
//     * 获取所有饲料的粗略信息
//     *
//     * @return
//     */
//    @GetMapping("/getAll")
//    public BaseResponse<List<FeedVO>> getAll(){
//        List<FeedVO> feedVOList = feedManagementService.getAll();
//        return ResultUtils.success(feedVOList);
//    }

}
