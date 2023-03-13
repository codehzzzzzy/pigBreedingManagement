package com.hzzzzzy.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

/**
 * 管理员
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 创建时间
     */
    private LocalDate createTime;

    /**
     * 更新时间
     */
    private LocalDate updateTime;

    /**
     * 是否删除(0.不删除;1.删除)
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 管理员类别(1.总管理员;2.猪舍管理员;3.猪只信息管理员;4.饲料管理员;5.进出库管理员)
     */
    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}