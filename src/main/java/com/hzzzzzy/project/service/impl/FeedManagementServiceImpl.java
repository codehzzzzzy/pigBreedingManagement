package com.hzzzzzy.project.service.impl;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzzzzzy.project.common.BaseResponse;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.common.ResultUtils;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.mapper.FeedManagementMapper;
import com.hzzzzzy.project.model.dto.feed.FeedAddRequest;
import com.hzzzzzy.project.model.dto.feed.FeedDeleteRequest;
import com.hzzzzzy.project.model.dto.feed.FeedManagementExcel;
import com.hzzzzzy.project.model.dto.feed.FeedUpdateRequest;
import com.hzzzzzy.project.model.dto.pig.PigExcel;
import com.hzzzzzy.project.model.dto.pig.PigUpdateRequest;
import com.hzzzzzy.project.model.entity.FeedManagement;
import com.hzzzzzy.project.model.entity.Hogring;
import com.hzzzzzy.project.model.entity.Pig;
import com.hzzzzzy.project.model.entity.User;
import com.hzzzzzy.project.model.vo.FeedGetOneVO;
import com.hzzzzzy.project.model.vo.FeedVO;
import com.hzzzzzy.project.model.vo.HoringVO;
import com.hzzzzzy.project.service.FeedManagementService;
import com.hzzzzzy.project.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static com.hzzzzzy.project.constant.UserConstant.*;

/**
* @author hzzzzzy
* @description 针对表【feed_management(饲料管理)】的数据库操作Service实现
* @createDate 2023-03-10 11:15:21
*/
@Service
public class FeedManagementServiceImpl extends ServiceImpl<FeedManagementMapper, FeedManagement>
    implements FeedManagementService {
    @Autowired
    private UserService userService;


    /**
     * 判断是否为当前模块管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        //判断是否为当前模块管理员
        int type = userService.getLoginUser(request).getType();
        if (type != FODDER_ADMIN && type != GENERAL_ADMIN){
            return false;
        }
        return true;
    }


    /**
     * 饲料管理员创建饲料信息
     *
     * @param feedAddRequest
     * @param request
     * @return
     */
    @Override
    public long add(FeedAddRequest feedAddRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (feedAddRequest.getHogringId()==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"所绑定的猪舍不存在");
        }
        FeedManagement feedManagement = new FeedManagement();
        BeanUtils.copyProperties(feedAddRequest,feedManagement);
        boolean flag = this.save(feedManagement);
        if (!flag){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return feedManagement.getId();
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
    protected List<FeedManagementExcel> dataTemplate() {
        ArrayList<FeedManagementExcel> feedManagementExcelArrayList = new ArrayList<>();
        List<FeedManagement> pigList = this.list();
        pigList.forEach((FeedManagement feedManagement)->{
            FeedManagementExcel feedManagementExcel = new FeedManagementExcel();
            BeanUtils.copyProperties(feedManagement,feedManagementExcel);
            feedManagementExcelArrayList.add(feedManagementExcel);
        });
        return feedManagementExcelArrayList;
    }


    /**
     * 肉猪信息管理员更新肉猪信息
     *
     * @param feedUpdateRequest
     * @param request
     * @return
     */
    @Override
    public boolean update(FeedUpdateRequest feedUpdateRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (feedUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FeedManagement feedManagement = new FeedManagement();
        BeanUtils.copyProperties(feedUpdateRequest, feedManagement);
        //获取当前管理员id
        User loginUser = userService.getLoginUser(request);
        feedManagement.setFeeder(loginUser.getId());
        boolean result = this.updateById(feedManagement);
        return result;
    }

    /**
     * 饲料信息管理员 删除 饲料信息
     *
     * @param feedDeleteRequest
     * @param request
     * @return
     */
    @Override
    public boolean delete(FeedDeleteRequest feedDeleteRequest, HttpServletRequest request) {
        //判断是否为当前模块管理员
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        if (feedDeleteRequest == null || feedDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean flag = this.removeById(feedDeleteRequest.getId());
        return flag;
    }


    /**
     * 通过饲料id获取详细信息
     *
     * @param id
     * @return
     */
    @Override
    public FeedGetOneVO getDetailById(int id) {
        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FeedManagement feedManagement = this.getById(id);
        if (feedManagement == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        FeedGetOneVO feedGetOneVO = new FeedGetOneVO();
        BeanUtils.copyProperties(feedManagement,feedGetOneVO);
        //手动填充管理员类型信息
        Long feeder = feedGetOneVO.getFeeder();
        User user = userService.getById(feeder);
        Integer type = user.getType();
        if (type == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        feedGetOneVO.setFeeder_type(type);
        return feedGetOneVO;
    }


    /**
     * 获取所有饲料的粗略信息
     *
     */
    @Override
    public List<FeedVO> getAll() {
        List<FeedManagement> feedManagementList = this.list();
        if (feedManagementList == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        List<FeedVO> feedVOList = new ArrayList<>();
        feedManagementList.forEach((FeedManagement feedManagement) -> {
            FeedVO feedVO = new FeedVO();
            BeanUtils.copyProperties(feedManagement,feedVO);
            feedVOList.add(feedVO);
        });
        return feedVOList;
    }
}




