package com.hzzzzzy.project.model.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户视图
 *
 */
@Data
public class UserVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 管理员类别(1.总管理员;2.猪舍管理员;3.猪只信息管理员;4.饲料管理员;5.进出库管理员)
     */
    private Integer type;

    private static final long serialVersionUID = 1L;
}