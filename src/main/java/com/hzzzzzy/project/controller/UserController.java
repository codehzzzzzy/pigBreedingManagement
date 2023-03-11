package com.hzzzzzy.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.hzzzzzy.project.common.BaseResponse;
import com.hzzzzzy.project.model.dto.user.UserDeleteRequest;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.common.ResultUtils;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.model.dto.user.*;
import com.hzzzzzy.project.model.entity.User;
import com.hzzzzzy.project.model.vo.UserVO;
import com.hzzzzzy.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户接口
 *
 * @author hzzzzzy
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String account = userRegisterRequest.getAccount();
        String pwd = userRegisterRequest.getPwd();
        String checkPwd = userRegisterRequest.getCheckPwd();
        Integer type = userRegisterRequest.getType();
        if (StringUtils.isAnyBlank(account, pwd, checkPwd) && type>=1 && type <=5) {
            return null;
        }
        long result = userService.userRegister(account, pwd, checkPwd,type);
        return ResultUtils.success(result);
    }

    /**
     * 登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String account = userLoginRequest.getAccount();
        String pwd = userLoginRequest.getPwd();
        if (StringUtils.isAnyBlank(account, pwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(account, pwd, request);
        return ResultUtils.success(user);
    }

    /**
     * 注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录管理员
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginObj(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.success(userVO);
    }


    /**
     * 总管理员 创建 普通管理员
     *
     * @param userAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> add(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = userService.add(userAddRequest,request);
        return ResultUtils.success(id);
    }

    /**
     * 总管理员 删除 普通管理员
     *
     * @param userDeleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        boolean flag = userService.delete(userDeleteRequest, request);
        return ResultUtils.success(flag);
    }

    /**
     * 总管理员 更新 普通管理员
     *
     * @param userUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        boolean result = userService.update(userUpdateRequest, request);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取管理员
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<UserVO> getUserById(int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.success(userVO);
    }


    /**
     * 获取管理员列表
     *
     * @param userQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<UserVO>> listUser(UserQueryRequest userQueryRequest, HttpServletRequest request) {
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userService.list(queryWrapper);
        List<UserVO> userVOList = userList.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(userVOList);
    }


    /**
     * 分页获取管理员列表
     *
     * @param userQueryRequest
     * @return
     */
    @GetMapping("/list/page/{current}/{size}")
    public BaseResponse<Page<UserVO>> listUserByPage(UserQueryRequest userQueryRequest,@PathVariable long current,@PathVariable long size) {
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
            current = userQueryRequest.getCurrent();
            size = userQueryRequest.getPageSize();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        Page<User> userPage = userService.page(new Page<>(current, size), queryWrapper);
        Page<UserVO> userVOPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserVO> userVOList = userPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }
}
