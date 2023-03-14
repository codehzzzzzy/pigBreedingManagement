package com.hzzzzzy.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzzzzzy.project.common.ResultUtils;
import com.hzzzzzy.project.model.dto.user.UserDeleteRequest;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.constant.UserConstant;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.mapper.UserMapper;
import com.hzzzzzy.project.model.dto.user.UserAddRequest;
import com.hzzzzzy.project.model.dto.user.UserQueryRequest;
import com.hzzzzzy.project.model.dto.user.UserUpdateRequest;
import com.hzzzzzy.project.model.entity.User;
import com.hzzzzzy.project.model.vo.UserVO;
import com.hzzzzzy.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author hzzzzzy
 * @description 针对表【User(管理员信息)】的数据库操作Service实现
 * @createDate 2023-03-10 11:15:21
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 利用框架，自动注入(创建)userMapper
    @Autowired
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "hzzzzzy";

    /**
     * 注册
     *
     * @param account 用户账户
     * @param pwd 用户密码
     * @param checkPwd 校验密码
     * @param type 管理员类型
     * @return
     */
    @Override
    public long userRegister(String account, String pwd, String checkPwd,Integer type) {
        // 校验
        if (StringUtils.isAnyBlank(account, pwd, checkPwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 账号长度是否小于4位
        if (account.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        // 密码和校验密码是否小于8位
        if (pwd.length() < 8 || checkPwd.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 管理员类型是否正确
        if (!(type>=1 && type <=5)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "管理员类型错误");
        }
        // 密码和校验密码相同
        if (!pwd.equals(checkPwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (account.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", account);
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 使用md5进行加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + pwd).getBytes());
            // 插入数据
            User user = new User();
            user.setAccount(account);
            user.setPwd(encryptPassword);
            user.setType(type);
            //this.save(操作数据库): 使用userService的save方法，将user保存进数据库
            boolean saveResult = this.save(user);
            //如果保存结果不为true，抛出业务异常 BusinessException
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }


    /**
     * 登录
     *
     * @param account  用户账户
     * @param pwd 用户密码
     * @param request
     * @return
     */
    @Override
    public User userLogin(String account, String pwd, HttpServletRequest request) {
        // 校验
        if (StringUtils.isAnyBlank(account, pwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (account.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (pwd.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + pwd).getBytes());
        /**
         * 查询用户是否存在
         * QueryWrapper代表是一个查询类
         * eq("account", account)表示数据库中的account字段和 前端传到后端的account数据一样 才能查询到
         *
         * userMapper.selectOne 表示查询一个对象
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("account", account)
                .eq("pwd", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 记录用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        return user;
    }


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 通过之前设置的登录态判断是否已经登录
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        //如果未登录，抛出异常
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // this.getById: 从数据库查询
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }


    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        // 检验登录态是否存在
        if (request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }


    /**
     * 总管理员 创建 普通管理员
     *
     * @param userAddRequest
     * @return
     */
    @Override
    public long add(UserAddRequest userAddRequest, HttpServletRequest request) {
        User user = new User();
        // 判断当前角色是否为总管理员
        User loginUser = getLoginUser(request);
        if (loginUser.getType() != UserConstant.GENERAL_ADMIN){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // BeanUtils.copyProperties(A,B) 表示将A对象中的数据复制到B
        BeanUtils.copyProperties(userAddRequest, user);
        // this.save 表示将user对象保存再数据库中
        boolean result = this.save(user);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return user.getId();
    }


    /**
     * 总管理员 删除 普通管理员
     *
     * @param userDeleteRequest
     * @param request
     * @return
     */
    @Override
    public boolean delete(UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        // 判断当前角色是否为总管理员
        User loginUser = getLoginUser(request);
        if (loginUser.getType() != UserConstant.GENERAL_ADMIN) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 判断前端传过来的数据是否为空
        if (userDeleteRequest == null || userDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // this.removeById 表示将id对应的user对象 在数据库中删除
        boolean flag = this.removeById(userDeleteRequest.getId());
        return flag;
    }


    /**
     * 总管理员 更新 普通管理员
     *
     * @param userUpdateRequest
     * @param request
     * @return
     */
    @Override
    public boolean update(UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        //判断当前角色是否为总管理员
        User loginUser = getLoginUser(request);
        if (loginUser.getType() != UserConstant.GENERAL_ADMIN) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 判断前端传过来的数据是否为空
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建需要存进数据库中的user对象
        User user = new User();
        // 将userUpdateRequest对象中的内容拷贝到user中，如何将user通过updateById存进数据库中
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = this.updateById(user);
        return result;
    }


    /**
     * 分页获取管理员列表
     *
     * @param userQueryRequest
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<UserVO> getAll(UserQueryRequest userQueryRequest, long current, long size) {
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
            current = userQueryRequest.getCurrent();
            size = userQueryRequest.getPageSize();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        // 使用Mybatis-plus的分页插件 https://baomidou.com/pages/97710a/#page
        Page<User> userPage = this.page(new Page<>(current, size), queryWrapper);
        // 创建PageDTO，存入当前页数，每页显示条数，总条数
        Page<UserVO> userVOPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        // getRecords: 获取查询数据列表
        // 此处使用java8中的stream流 具体看https://www.hzzzzzy.icu/2022/08/31/Stream%E6%B5%81/
        List<UserVO> userVOList = userPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        userVOPage.setRecords(userVOList);
        return userVOPage;
    }


    /**
     * 获取管理员列表
     *
     * @param userQueryRequest
     * @return
     */
    @Override
    public List<UserVO> listUser(UserQueryRequest userQueryRequest) {
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = this.list(queryWrapper);
        List<UserVO> userVOList = userList.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return userVOList;
    }
}




