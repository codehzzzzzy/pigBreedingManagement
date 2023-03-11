package com.hzzzzzy.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author hzzzzzy
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String account;

    private String pwd;

    private String checkPwd;

    private Integer type;
}
