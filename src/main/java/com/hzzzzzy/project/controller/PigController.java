package com.hzzzzzy.project.controller;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.hzzzzzy.project.common.BaseResponse;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.common.ExcelUtils;
import com.hzzzzzy.project.common.ResultUtils;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.model.dto.pig.PigAddRequest;
import com.hzzzzzy.project.model.dto.pig.PigExcel;
import com.hzzzzzy.project.model.dto.pig.PigUpdateRequest;
import com.hzzzzzy.project.model.vo.PigVo;
import com.hzzzzzy.project.service.PigService;
import com.hzzzzzy.project.model.entity.Pig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 肉猪信息接口
 *
 * @author hzzzzzy
 */
@RestController
@RequestMapping("/pig")
public class PigController {
    @Autowired
    private PigService pigService;

    /**
     * 多条件动态查询
     *
     * @param id
     * @param breed
     * @param age
     * @param gender
     * @param health
     * @param status
     * @param weight_pre 体重起始值
     * @param weight_suf 体重结束值
     * @param feedType
     * @return
     */
    @GetMapping("/searchBy")
    public BaseResponse<List<Pig>> searchBy(Integer id, String breed, Integer age, Integer gender, Integer health, Integer status, BigDecimal weight_pre, BigDecimal weight_suf, String feedType){
        List<Pig> pigList = pigService.searchBy(id, breed, age, gender, health, status, weight_pre, weight_suf,feedType);
        if (pigList==null){
            return new BaseResponse<>(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(pigList);
    }


    /**
     * 关联查询肉猪和所在猪舍信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public BaseResponse<PigVo> getById(int id){
        PigVo pigVo = pigService.getAllById(id);
        return ResultUtils.success(pigVo);
    }


    /**
     * 肉猪信息管理员更新肉猪信息
     *
     * @param pigUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody PigUpdateRequest pigUpdateRequest, HttpServletRequest request){
        boolean flag = pigService.update(pigUpdateRequest, request);
        return ResultUtils.success(flag);
    }


    /**
     * 肉猪信息管理员创建肉猪信息
     *
     * @param pigAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> add(@RequestBody PigAddRequest pigAddRequest, HttpServletRequest request) {
        if (pigAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = pigService.add(pigAddRequest,request);
        return ResultUtils.success(id);
    }


    /**
     * 导出excel
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response){
        ExcelWriterSheetBuilder sheetBuilder = ExcelUtils.export(response);
        pigService.export(response,sheetBuilder);
        sheetBuilder.doWrite(dataTemplate());
    }

    protected List<PigExcel> dataTemplate() {
        ArrayList<PigExcel> pigExcels = new ArrayList<>();
        List<Pig> pigList = pigService.list();
        pigList.forEach((Pig pig)->{
            PigExcel pigExcel = new PigExcel();
            BeanUtils.copyProperties(pig,pigExcel);
            pigExcels.add(pigExcel);
        });
        return pigExcels;
    }



















}
