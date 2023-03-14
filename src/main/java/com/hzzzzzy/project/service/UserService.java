package com.hzzzzzy.project.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzzzzzy.project.model.dto.user.UserDeleteRequest;
import com.hzzzzzy.project.model.dto.user.UserAddRequest;
import com.hzzzzzy.project.model.dto.user.UserQueryRequest;
import com.hzzzzzy.project.model.dto.user.UserUpdateRequest;
import com.hzzzzzy.project.model.entity.User;
import com.hzzzzzy.project.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 *
 * @author hzzzzzy
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param account   用户账户
     * @param pwd  用户密码
     * @param checkPwd 校验密码
     * @param type 管理员类型
     * @return 新用户 id
     */
    long userRegister(String account, String pwd, String checkPwd,Integer type);

    /**
     * 用户登录
     *
     * @param account  用户账户
     * @param pwd 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String account, String pwd, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 总管理员 创建 普通管理员
     *
     * @param userAddRequest
     * @param request
     * @return
     */
    long add(UserAddRequest userAddRequest, HttpServletRequest request);

    /**
     * 总管理员 删除 普通管理员
     *
     * @param userDeleteRequest
     * @param request
     * @return
     */
    boolean delete(UserDeleteRequest userDeleteRequest, HttpServletRequest request);


    /**
     * 总管理员 更新 普通管理员
     *
     * @param userUpdateRequest
     * @param request
     * @return
     */
    boolean update(UserUpdateRequest userUpdateRequest, HttpServletRequest request);


    /**
     * 分页获取管理员列表
     *
     * @param userQueryRequest
     * @param current
     * @param size
     * @return
     */
    Page<UserVO> getAll(UserQueryRequest userQueryRequest, long current, long size);


    /**
     * 获取管理员列表
     *
     * @param userQueryRequest
     */
    List<UserVO> listUser(UserQueryRequest userQueryRequest);
}
