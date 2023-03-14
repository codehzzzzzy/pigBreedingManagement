package com.hzzzzzy.project.controller;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzzzzzy.project.common.BaseResponse;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.common.ExcelUtils;
import com.hzzzzzy.project.common.ResultUtils;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.model.dto.feed.FeedAddRequest;
import com.hzzzzzy.project.model.dto.feed.FeedDeleteRequest;
import com.hzzzzzy.project.model.dto.feed.FeedUpdateRequest;
import com.hzzzzzy.project.model.dto.hogring.HogringAddRequest;
import com.hzzzzzy.project.model.dto.hogring.HogringDeleteRequest;
import com.hzzzzzy.project.model.dto.hogring.HogringUpdateRequest;
import com.hzzzzzy.project.model.dto.hogring.PigIdListGetRequest;
import com.hzzzzzy.project.model.entity.Hogring;
import com.hzzzzzy.project.model.entity.Pig;
import com.hzzzzzy.project.model.vo.HoringVO;
import com.hzzzzzy.project.model.vo.PigInHoringVO;
import com.hzzzzzy.project.service.FeedManagementService;
import com.hzzzzzy.project.service.HogringService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 猪舍管理接口
 *
 * @author hzzzzzy
 */
@RestController
@RequestMapping("/hogring")
public class HogringController {
    @Autowired
    private HogringService hogringService;

    /**
     * 猪舍管理员 创建 新猪舍
     *
     * @param hogringAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> add(@RequestBody HogringAddRequest hogringAddRequest, HttpServletRequest request) {
        if (hogringAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = hogringService.add(hogringAddRequest,request);
        return ResultUtils.success(id);
    }


    /**
     * 导出excel
     *
     * @param response
     */
    @RequestMapping("/export")
    public BaseResponse<Boolean> exportExcel(HttpServletResponse response){
        ExcelWriterSheetBuilder sheetBuilder = ExcelUtils.export(response);
        boolean flag = hogringService.export(response, sheetBuilder);
        return ResultUtils.success(flag);
    }


    /**
     * 猪舍信息管理员 更新 猪舍信息
     *
     * @param hogringUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody HogringUpdateRequest hogringUpdateRequest, HttpServletRequest request){
        boolean flag = hogringService.update(hogringUpdateRequest,request);
        return ResultUtils.success(flag);
    }


    /**
     * 猪舍信息管理员 删除 猪舍信息
     *
     * @param hogringDeleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody HogringDeleteRequest hogringDeleteRequest, HttpServletRequest request) {
        boolean flag = hogringService.delete(hogringDeleteRequest, request);
        return ResultUtils.success(flag);
    }


    /**
     * 获取猪舍内肉猪列表
     *
     * @param pigIdListGetRequest
     * @param request
     * @return
     */
    @GetMapping("/getPigList")
    public BaseResponse<List<PigInHoringVO>> getPigList(@RequestBody PigIdListGetRequest pigIdListGetRequest, HttpServletRequest request){
        List<PigInHoringVO> pigList = hogringService.getPigList(pigIdListGetRequest, request);
        return ResultUtils.success(pigList);
    }


    /**
     * 通过猪舍id获取猪舍信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public BaseResponse<HoringVO> getById(int id){
        if (id <= 0){
            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
        }
        Hogring hogring = hogringService.getById(id);
        if (hogring == null){
            return new BaseResponse<>(ErrorCode.NOT_FOUND_ERROR);
        }
        HoringVO horingVO = new HoringVO();
        BeanUtils.copyProperties(hogring,horingVO);
        return ResultUtils.success(horingVO);
    }


    /**
     * 分页获取所有猪舍信息
     *
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getAll/{current}/{size}")
    public BaseResponse<Page<HoringVO>> getAll(@PathVariable long current, @PathVariable long size){
        Page<HoringVO> page = hogringService.getAll(current, size);
        if (page == null){
            return new BaseResponse<>(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(page);
    }



}
