package com.hzzzzzy.project.service.impl;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.mapper.PigMapper;
import com.hzzzzzy.project.model.dto.pig.PigAddRequest;
import com.hzzzzzy.project.model.dto.pig.PigExcel;
import com.hzzzzzy.project.model.dto.pig.PigUpdateRequest;
import com.hzzzzzy.project.model.entity.Hogring;
import com.hzzzzzy.project.model.entity.Pig;
import com.hzzzzzy.project.model.vo.PigVo;
import com.hzzzzzy.project.service.HogringService;
import com.hzzzzzy.project.service.PigService;
import com.hzzzzzy.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.hzzzzzy.project.constant.PigConstant.*;
import static com.hzzzzzy.project.constant.PigConstant.ASSIST_IN_JUDGMENT;
import static com.hzzzzzy.project.constant.UserConstant.GENERAL_ADMIN;
import static com.hzzzzzy.project.constant.UserConstant.PIG_ADMIN;

/**
* @author hzzzzzy
* @description 针对表【pig(猪只信息)】的数据库操作Service实现
* @createDate 2023-03-10 11:15:21
*/
@Service
@Slf4j
public class PigServiceImpl extends ServiceImpl<PigMapper, Pig>
    implements PigService {
    @Resource
    private UserService userService;
    @Resource
    private HogringService hogringService;
    /**
     * 判断是否为当前模块管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        //判断是否为当前模块管理员
        int type = userService.getLoginUser(request).getType();
        if (type != PIG_ADMIN && type != GENERAL_ADMIN){
            return false;
        }
        return true;
    }


    /**
     * 肉猪信息管理员 更新 肉猪信息
     *
     * @param pigUpdateRequest
     * @param request
     * @return
     */
    @Override
    public boolean update(PigUpdateRequest pigUpdateRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (pigUpdateRequest == null || pigUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Pig pig = new Pig();
        BeanUtils.copyProperties(pigUpdateRequest, pig);
        boolean result = this.updateById(pig);
        return result;
    }


    /**
     * 肉猪信息管理员创建肉猪信息
     *
     * @param pigAddRequest
     * @param request
     * @return
     */
    @Override
    public long add(PigAddRequest pigAddRequest, HttpServletRequest request) {
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        Pig pig = new Pig();
        BeanUtils.copyProperties(pigAddRequest, pig);
        boolean flag = this.save(pig);
        if (!flag){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return pig.getId();
    }


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
    @Override
    public List<Pig> searchBy(Integer id, String breed, Integer age, Integer gender, Integer health, Integer status, BigDecimal weight_pre,BigDecimal weight_suf,String feedType) {
        LambdaQueryWrapper<Pig> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件
        queryWrapper.eq(id != null,Pig::getId,id);
        queryWrapper.eq(StringUtils.isNotBlank(breed),Pig::getBreed,breed);
        queryWrapper.eq((age != null),Pig::getAge,age);
        queryWrapper.eq(gender == MALE || gender == FEMALE,Pig::getGender,gender);
        queryWrapper.eq(health == UNHEALTHY || health == SUB_HEALTHY || health == HEALTHY,Pig::getHealth,health);
        queryWrapper.eq(status == NOT_OUT_OF_STOCK || status == OUT_OF_STOCK,Pig::getStatus,status);
        if (weight_pre != null){
            queryWrapper.ge(weight_pre.compareTo(ASSIST_IN_JUDGMENT) > -1,Pig::getWeight,weight_pre);
        }
        if (weight_suf != null){
            queryWrapper.le(weight_suf.compareTo(ASSIST_IN_JUDGMENT) > -1,Pig::getWeight,weight_suf);
        }
        queryWrapper.eq(StringUtils.isNotBlank(feedType),Pig::getFeedType,feedType);
        return this.list(queryWrapper);
    }


    /**
     * 关联查询肉猪和所在猪舍信息
     *
     * @param id
     * @return
     */
    @Override
    public PigVo getAllById(int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PigVo pigVo = new PigVo();
        Pig pig = this.getById(id);
        Integer hogringId = pig.getHogringId();
        Hogring hogring = hogringService.getById(hogringId);
        BeanUtils.copyProperties(hogring,pigVo);
        BeanUtils.copyProperties(pig,pigVo);
        return pigVo;
    }


    /**
     * 导出Excel
     *
     * @param response
     * @param sheetBuilder
     */
    @Override
    public void export(HttpServletResponse response,ExcelWriterSheetBuilder sheetBuilder) {
        sheetBuilder.doWrite(dataTemplate());
    }
    //处理数据
    protected List<PigExcel> dataTemplate() {
        ArrayList<PigExcel> pigExcels = new ArrayList<>();
        List<Pig> pigList = this.list();
        pigList.forEach((Pig pig)->{
            PigExcel pigExcel = new PigExcel();
            BeanUtils.copyProperties(pig,pigExcel);
            pigExcels.add(pigExcel);
        });
        return pigExcels;
    }
}




