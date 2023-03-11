package com.hzzzzzy.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author hzzzzzy
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 账号
     */
    private String account;

    /**
     * 类型: 管理员类别(1.总管理员;2.猪舍管理员;3.猪只信息管理员;4.饲料管理员;5.进出库管理员)
     */
    private Integer type;

    /**
     * 密码
     */
    private String pwd;

    private static final long serialVersionUID = 1L;
}