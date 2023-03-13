package com.hzzzzzy.project.model.dto.user;

import com.hzzzzzy.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 用户查询请求
 *
 * @author hzzzzzy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 类型: 管理员类别(1.总管理员;2.猪舍管理员;3.猪只信息管理员;4.饲料管理员;5.进出库管理员)
     */
    private Integer type;

    /**
     * 创建时间
     */
    private LocalDate createTime;

    /**
     * 更新时间
     */
    private LocalDate updateTime;

    private static final long serialVersionUID = 1L;
}