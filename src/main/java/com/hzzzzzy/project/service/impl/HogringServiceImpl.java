package com.hzzzzzy.project.service.impl;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.mapper.HogringMapper;
import com.hzzzzzy.project.model.dto.hogring.*;
import com.hzzzzzy.project.model.dto.pig.PigExcel;
import com.hzzzzzy.project.model.entity.Hogring;
import com.hzzzzzy.project.model.entity.Pig;
import com.hzzzzzy.project.model.entity.User;
import com.hzzzzzy.project.model.vo.PigInHoringVO;
import com.hzzzzzy.project.service.HogringService;
import com.hzzzzzy.project.service.PigService;
import com.hzzzzzy.project.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.hzzzzzy.project.constant.UserConstant.*;

/**
* @author hzzzzzy
* @description 针对表【hogring(猪舍)】的数据库操作Service实现
* @createDate 2023-03-10 11:15:21
*/
@Service
public class HogringServiceImpl extends ServiceImpl<HogringMapper, Hogring>
    implements HogringService {
    @Autowired
    private UserService userService;
    @Lazy
    @Autowired
    private PigService pigService;
    /**
     * 判断是否为当前模块管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        //判断是否为当前模块管理员
        int type = userService.getLoginUser(request).getType();
        return type == PIGSTY_ADMIN || type == GENERAL_ADMIN;
    }

    /**
     * 猪舍管理员 创建 新猪舍
     *
     * @param hogringAddRequest
     * @param request
     * @return
     */
    @Override
    public long add(HogringAddRequest hogringAddRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (hogringAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        //将猪舍id列表转为json字符串
        List<Integer> pigIdList = hogringAddRequest.getPigId();
        Gson gson = new Gson();
        String pigIdListJson = gson.toJson(pigIdList);
        //手动填充
        Hogring hogring = new Hogring();
        BigDecimal hogringArea = hogringAddRequest.getHogringArea();
        Integer hogringStatus = hogringAddRequest.getHogringStatus();
        Integer hogringCapacity = hogringAddRequest.getHogringCapacity();
        User loginUser = userService.getLoginUser(request);
        hogring.setUserId(loginUser.getId());
        hogring.setHogringArea(hogringArea);
        hogring.setHogringStatus(hogringStatus);
        hogring.setHogringCapacity(hogringCapacity);
        hogring.setPigId(pigIdListJson);
        boolean flag = this.save(hogring);
        if (!flag){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return hogring.getHogringId();
    }


    /**
     * 导出Excel
     *
     * @param response
     * @param sheetBuilder
     */
    @Override
    public boolean export(HttpServletResponse response,ExcelWriterSheetBuilder sheetBuilder) {
        sheetBuilder.doWrite(dataTemplate());
        return true;
    }
    //处理数据
    protected List<HogringExcel> dataTemplate() {
        ArrayList<HogringExcel> hogringExcels = new ArrayList<>();
        List<Hogring> pigList = this.list();
        pigList.forEach((Hogring hogring)->{
            HogringExcel hogringExcel = new HogringExcel();
            BeanUtils.copyProperties(hogring,hogringExcel);
            hogringExcels.add(hogringExcel);
        });
        return hogringExcels;
    }


    /**
     * 猪舍信息管理员 更新 猪舍信息
     *
     * @param hogringUpdateRequest
     * @param request
     * @return
     */
    @Override
    public boolean update(HogringUpdateRequest hogringUpdateRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (hogringUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        List<Integer> pigId = hogringUpdateRequest.getPigId();
        Gson gson = new Gson();
        String pigIdListJson = gson.toJson(pigId);
        Hogring hogring = new Hogring();
        //手动填充数据
        Integer hogringStatus = hogringUpdateRequest.getHogringStatus();
        BigDecimal hogringArea = hogringUpdateRequest.getHogringArea();
        Integer hogringCapacity = hogringUpdateRequest.getHogringCapacity();
        hogring.setHogringStatus(hogringStatus);
        hogring.setHogringArea(hogringArea);
        hogring.setHogringCapacity(hogringCapacity);
        hogring.setPigId(pigIdListJson);
        boolean flag = this.updateById(hogring);
        if (!flag){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"插入信息失败");
        }
        return flag;
    }


    /**
     * 猪舍信息管理员 删除 猪舍信息
     *
     * @param hogringDeleteRequest
     * @param request
     * @return
     */
    @Override
    public boolean delete(HogringDeleteRequest hogringDeleteRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (hogringDeleteRequest == null || hogringDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean flag = this.removeById(hogringDeleteRequest.getId());
        if (!flag){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return flag;
    }


    /**
     * 获取猪舍内肉猪列表
     *
     * @param pigIdListGetRequest
     * @param request
     * @return
     */
    @Override
    public List<PigInHoringVO> getPigList(PigIdListGetRequest pigIdListGetRequest, HttpServletRequest request) {
        if (pigIdListGetRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        List<Integer> pigIdList = pigIdListGetRequest.getPigId();
        List<PigInHoringVO> pigInHoringVOList = new ArrayList<>();
        pigIdList.forEach((Integer pigId)->{
            Pig pig = pigService.getById(pigId);
            PigInHoringVO pigInHoringVO = new PigInHoringVO();
            BeanUtils.copyProperties(pig,pigInHoringVO);
            pigInHoringVOList.add(pigInHoringVO);
        });
        return pigInHoringVOList;
    }


}




