package com.hzzzzzy.project.service.impl;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.hzzzzzy.project.common.BaseResponse;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.common.ResultUtils;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.mapper.PigInventoryMapper;
import com.hzzzzzy.project.model.dto.PigInventory.*;
import com.hzzzzzy.project.model.dto.hogring.HogringDeleteRequest;
import com.hzzzzzy.project.model.dto.pig.PigExcel;
import com.hzzzzzy.project.model.entity.Hogring;
import com.hzzzzzy.project.model.entity.Pig;
import com.hzzzzzy.project.model.entity.PigInventory;
import com.hzzzzzy.project.model.vo.PigDetailVO;
import com.hzzzzzy.project.model.vo.PigInventoryGetOneVO;
import com.hzzzzzy.project.model.vo.PigInventoryVO;
import com.hzzzzzy.project.model.vo.PigVO;
import com.hzzzzzy.project.service.HogringService;
import com.hzzzzzy.project.service.PigInventoryService;
import com.hzzzzzy.project.service.PigService;
import com.hzzzzzy.project.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hzzzzzy.project.constant.UserConstant.*;

/**
* @author hzzzzzy
* @description 针对表【pig_inventory(猪只进出库管理)】的数据库操作Service实现
* @createDate 2023-03-12 13:26:03
*/
@Service
public class PigInventoryServiceImpl extends ServiceImpl<PigInventoryMapper, PigInventory>
        implements PigInventoryService{
    @Autowired
    private PigService pigService;
    @Autowired
    private UserService userService;
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
        if (type != PIG_INVENTORY_ADMIN && type != GENERAL_ADMIN){
            return false;
        }
        return true;
    }


    /**
     * 肉猪进出库管理员 创建 肉猪进出信息
     *
     * @param pigInventoryAddRequest
     * @param request
     * @return
     */
    @Override
    public Integer add(PigInventoryAddRequest pigInventoryAddRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        Integer pigId = pigInventoryAddRequest.getPigId();
        Integer hogringId = pigInventoryAddRequest.getHogringId();
        Hogring hogring = hogringService.getById(hogringId);
        Pig pig = pigService.getById(pigId);
        if (pig == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"不存在该编号的肉猪");
        }
        if (hogring == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"不存在该编号的猪舍");
        }
        //判断该肉猪是否已经存储
        QueryWrapper<PigInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pigId",pigId);
        PigInventory inventory = this.getOne(queryWrapper);
        if (inventory != null){
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_ERROR,"该肉猪id已存在");
        }
        PigInventory pigInventory = new PigInventory();
        BeanUtils.copyProperties(pigInventoryAddRequest,pigInventory);
        boolean flag = this.save(pigInventory);
        if (!flag){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return pigInventory.getId();
    }


    /**
     * 肉猪进出库管理员 批量创建 肉猪进出信息
     *
     * @param pigInventoryAddBatchRequest
     * @param request
     * @return
     */
    @Override
    public List<Integer> addBatch(PigInventoryAddBatchRequest pigInventoryAddBatchRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (pigInventoryAddBatchRequest == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //创建辅助对象
        PigInventoryAddBatchAuxiliary auxiliary = new PigInventoryAddBatchAuxiliary();
        BeanUtils.copyProperties(pigInventoryAddBatchRequest,auxiliary);
        List<Integer> pigIdList = pigInventoryAddBatchRequest.getPigIdList();
        ArrayList<Integer> pigInventoryIdList = new ArrayList<>();
        //遍历，将pigId放进pigInventory
        pigIdList.forEach(pigId->{
            PigInventory pigInventory = new PigInventory();
            BeanUtils.copyProperties(auxiliary,pigInventory);
            pigInventory.setPigId(pigId);
            boolean flag = this.save(pigInventory);
            if (!flag){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            pigInventoryIdList.add(pigInventory.getId());
        });
        return pigInventoryIdList;
    }


    /**
     * 导出excel
     *
     * @param response
     * @param sheetBuilder
     * @return
     */
    @Override
    public boolean export(HttpServletResponse response,ExcelWriterSheetBuilder sheetBuilder) {
        sheetBuilder.doWrite(dataTemplate());
        return true;
    }
    //处理数据
    protected List<PigInventoryExcel> dataTemplate() {
        ArrayList<PigInventoryExcel> pigInventoryExcels = new ArrayList<>();
        List<PigInventory> pigInventoryList = this.list();
        pigInventoryList.forEach((PigInventory pigInventory)->{
            PigInventoryExcel pigInventoryExcel = new PigInventoryExcel();
            BeanUtils.copyProperties(pigInventory,pigInventoryExcel);
            pigInventoryExcels.add(pigInventoryExcel);
        });
        return pigInventoryExcels;
    }


    /**
     * 肉猪进出库管理员 更新 肉猪进出信息
     *
     * @param pigInventoryUpdateRequest
     * @param request
     * @return
     */
    @Override
    public boolean update(PigInventoryUpdateRequest pigInventoryUpdateRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (pigInventoryUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        PigInventory pigInventory = new PigInventory();
        BeanUtils.copyProperties(pigInventoryUpdateRequest,pigInventory);
        boolean flag = this.save(pigInventory);
        if (!flag){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新信息失败");
        }
        return flag;
    }


    /**
     * 肉猪进出库管理员 删除 肉猪进出信息
     *
     * @param pigInventoryDeleteRequest
     * @param request
     * @return
     */
    @Override
    public boolean delete(PigInventoryDeleteRequest pigInventoryDeleteRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (pigInventoryDeleteRequest == null || pigInventoryDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean flag = this.removeById(pigInventoryDeleteRequest.getId());
        if (!flag){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return flag;
    }


    /**
     * 获取肉猪进出的详细信息
     *
     * @param id
     * @return
     */
    @Override
    public PigInventoryGetOneVO getDetailById(int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PigInventoryGetOneVO pigInventoryGetOneVO = new PigInventoryGetOneVO();
        PigInventory pigInventory = this.getById(id);
        if (pigInventory == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Integer pigId = pigInventory.getPigId();
        Pig pig = pigService.getById(pigId);
        if (pig == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"该id对应的肉猪不存在");
        }
        BeanUtils.copyProperties(pigInventory,pigInventoryGetOneVO);
        BeanUtils.copyProperties(pig,pigInventoryGetOneVO);
        return pigInventoryGetOneVO;
    }


    /**
     * 分页获取所有肉猪进出库信息列表
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<PigInventoryVO> getAll(long current, long size) {
        // 创建分页构造器对象
        Page<PigInventory> pageInfo = new Page<>(current,size);
        Page<PigInventoryVO> dtoPage = new Page<>();
        QueryWrapper<PigInventory> queryWrapper = new QueryWrapper<>();
        this.page(pageInfo,queryWrapper);

        // 拷贝对象
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<PigInventoryVO> pigInventoryVOList = pageInfo.getRecords().stream().map((PigInventory pigInventory) -> {
            PigInventoryVO pigInventoryVO = new PigInventoryVO();
            BeanUtils.copyProperties(pigInventory, pigInventoryVO);
            return pigInventoryVO;
        }).collect(Collectors.toList());
        dtoPage.setRecords(pigInventoryVOList);
        return dtoPage;
    }


}




