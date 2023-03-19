package com.hzzzzzy.project.controller;


import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzzzzzy.project.common.BaseResponse;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.common.ExcelUtils;
import com.hzzzzzy.project.common.ResultUtils;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.model.dto.feed.FeedAddRequest;
import com.hzzzzzy.project.model.dto.feed.FeedDeleteRequest;
import com.hzzzzzy.project.model.dto.feed.FeedUpdateRequest;
import com.hzzzzzy.project.model.dto.pig.PigAddRequest;
import com.hzzzzzy.project.model.dto.pig.PigDeleteRequest;
import com.hzzzzzy.project.model.dto.pig.PigUpdateRequest;
import com.hzzzzzy.project.model.entity.FeedManagement;
import com.hzzzzzy.project.model.entity.Hogring;
import com.hzzzzzy.project.model.vo.FeedGetOneVO;
import com.hzzzzzy.project.model.vo.FeedVO;
import com.hzzzzzy.project.model.vo.HoringVO;
import com.hzzzzzy.project.service.FeedManagementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 饲料管理接口
 *
 * @author hzzzzzy
 */
@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedManagementService feedManagementService;

    /**
     * 饲料管理员创建饲料信息
     *
     * @param feedAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> add(@RequestBody FeedAddRequest feedAddRequest, HttpServletRequest request) {
        if (feedAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = feedManagementService.add(feedAddRequest,request);
        return ResultUtils.success(id);
    }

    /**
     * 导出excel
     *
     * @param response
     */
    @GetMapping("/export")
    public BaseResponse<Boolean> exportExcel(HttpServletResponse response){
        ExcelWriterSheetBuilder sheetBuilder = ExcelUtils.export(response);
        boolean flag = feedManagementService.export(response, sheetBuilder);
        return ResultUtils.success(flag);
    }


    /**
     * 饲料信息管理员 更新 饲料信息
     *
     * @param feedUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody FeedUpdateRequest feedUpdateRequest, HttpServletRequest request){
        boolean flag = feedManagementService.update(feedUpdateRequest, request);
        return ResultUtils.success(flag);
    }


    /**
     * 饲料信息管理员 删除 饲料信息
     *
     * @param feedDeleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody FeedDeleteRequest feedDeleteRequest, HttpServletRequest request) {
        boolean flag = feedManagementService.delete(feedDeleteRequest, request);
        return ResultUtils.success(flag);
    }


    /**
     * 通过饲料id获取详细信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public BaseResponse<FeedGetOneVO> getById(int id){
        FeedGetOneVO feedVO = feedManagementService.getDetailById(id);
        return ResultUtils.success(feedVO);
    }


    /**
     * 分页获取所有饲料的粗略信息
     *
     * @return
     */
    @GetMapping("/getAll/{current}/{size}")
    public BaseResponse<Page<FeedVO>> getAll(@PathVariable long current, @PathVariable long size){
        Page<FeedVO> page= feedManagementService.getAll(current,size);
        if (page == null){
            return new BaseResponse<>(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(page);
    }

}
