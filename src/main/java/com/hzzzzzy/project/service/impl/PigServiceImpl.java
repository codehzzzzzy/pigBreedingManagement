package com.hzzzzzy.project.service.impl;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.constant.UserConstant;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.mapper.PigMapper;
import com.hzzzzzy.project.model.dto.pig.PigAddRequest;
import com.hzzzzzy.project.model.dto.pig.PigDeleteRequest;
import com.hzzzzzy.project.model.dto.pig.PigExcel;
import com.hzzzzzy.project.model.dto.pig.PigUpdateRequest;
import com.hzzzzzy.project.model.entity.Hogring;
import com.hzzzzzy.project.model.entity.Pig;
import com.hzzzzzy.project.model.entity.User;
import com.hzzzzzy.project.model.vo.PigDetailVO;
import com.hzzzzzy.project.model.vo.PigVO;
import com.hzzzzzy.project.model.vo.UserVO;
import com.hzzzzzy.project.service.HogringService;
import com.hzzzzzy.project.service.PigService;
import com.hzzzzzy.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;
    //@Autowired 表示自动注入该对象
    //@Lazy 表示延迟注入该对象 比如A中注入了B，B中注入了A，那么A，B就会循环加载，如果加上这个注解，就会在有需要的时候，再加载该对象，解决循环依赖的问题
    @Lazy
    @Autowired
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
    public List<PigVO> searchBy(Integer id, String breed, Integer age, Integer gender, Integer health, Integer status, BigDecimal weight_pre,BigDecimal weight_suf,String feedType) {

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
        List<Pig> pigList = this.list(queryWrapper);
        List<PigVO> pigVOList = new ArrayList<>();
        pigList.forEach((Pig pig)->{
            PigVO pigVO = new PigVO();
            BeanUtils.copyProperties(pig,pigVO);
            pigVOList.add(pigVO);
        });
        return pigVOList;
    }


    /**
     * 关联查询肉猪详细信息
     *
     * @param id
     * @return
     */
    @Override
    public PigDetailVO getDetailById(int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PigDetailVO pigVo = new PigDetailVO();
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
    public boolean export(HttpServletResponse response,ExcelWriterSheetBuilder sheetBuilder) {
        sheetBuilder.doWrite(dataTemplate());
        return true;
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

    /**
     * 肉猪信息管理员 删除 肉猪信息
     *
     * @param pigDeleteRequest
     * @param request
     * @return
     */
    @Override
    public boolean delete(PigDeleteRequest pigDeleteRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (pigDeleteRequest == null || pigDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean flag = this.removeById(pigDeleteRequest.getId());
        if (!flag){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除信息失败");
        }
        return flag;
    }


    /**
     * 分页获取所有肉猪详细信息列表
     *
     * @return
     */
    @Override
    public Page<PigVO> getAll(long current, long size) {
        // 创建分页构造器对象
        Page<PigVO> dtoPage = new Page<>();
        Page<Pig> pageInfo = new Page<>(current,size);
        QueryWrapper<Pig> queryWrapper = new QueryWrapper<>();
        this.page(pageInfo,queryWrapper);
        // 对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<PigVO> pigVOList = pageInfo.getRecords().stream().map((pig) -> {
            PigVO pigVO = new PigVO();
            BeanUtils.copyProperties(pig, pigVO);
            return pigVO;
        }).collect(Collectors.toList());
        dtoPage.setRecords(pigVOList);
        return dtoPage;
    }
}




