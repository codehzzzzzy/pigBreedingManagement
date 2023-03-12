package com.hzzzzzy.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzzzzzy.project.model.dto.user.UserDeleteRequest;
import com.hzzzzzy.project.common.ErrorCode;
import com.hzzzzzy.project.constant.UserConstant;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.mapper.UserMapper;
import com.hzzzzzy.project.model.dto.user.UserAddRequest;
import com.hzzzzzy.project.model.dto.user.UserUpdateRequest;
import com.hzzzzzy.project.model.entity.User;
import com.hzzzzzy.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author hzzzzzy
 * @description 针对表【User(管理员信息)】的数据库操作Service实现
 * @createDate 2023-03-10 11:15:21
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
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
        if (account.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (pwd.length() < 8 || checkPwd.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
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
            // 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + pwd).getBytes());
            // 插入数据
            User user = new User();
            user.setAccount(account);
            user.setPwd(encryptPassword);
            user.setType(type);
            boolean saveResult = this.save(user);
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
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("pwd", encryptPassword);
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
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能可以直接走缓存）
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
        //判断当前角色是否为总管理员
        User loginUser = getLoginUser(request);
        if (loginUser.getType() != UserConstant.GENERAL_ADMIN){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        BeanUtils.copyProperties(userAddRequest, user);
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
        //判断当前角色是否为总管理员
        User loginUser = getLoginUser(request);
        if (loginUser.getType() != UserConstant.GENERAL_ADMIN) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        if (userDeleteRequest == null || userDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
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
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = this.updateById(user);
        return result;
    }
}




