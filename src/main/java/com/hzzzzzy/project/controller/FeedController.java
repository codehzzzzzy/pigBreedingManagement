package com.hzzzzzy.project.controller;


import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.hzzzzzy.project.common.BaseResponse;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.common.ExcelUtils;
import com.hzzzzzy.project.common.ResultUtils;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.model.dto.feed.FeedAddRequest;
import com.hzzzzzy.project.model.dto.feed.FeedUpdateRequest;
import com.hzzzzzy.project.model.dto.pig.PigAddRequest;
import com.hzzzzzy.project.model.dto.pig.PigUpdateRequest;
import com.hzzzzzy.project.service.FeedManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * @throws Exception
     */
    @RequestMapping("/export")
    public BaseResponse<Boolean> exportExcel(HttpServletResponse response){
        ExcelWriterSheetBuilder sheetBuilder = ExcelUtils.export(response);
        boolean flag = feedManagementService.export(response, sheetBuilder);
        return ResultUtils.success(flag);
    }


    /**
     * 肉猪信息管理员更新肉猪信息
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


}
