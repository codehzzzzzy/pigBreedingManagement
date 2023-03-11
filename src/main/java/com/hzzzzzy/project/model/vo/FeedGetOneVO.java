package com.hzzzzzy.project.model.vo;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 饲料详细信息视图
 *
 * @author hzzzzzy
 */
@Data
public class FeedGetOneVO{
    /**
     * 饲料id
     */
    private Integer id;

    /**
     * 饲料种类
     */
    private String feedType;

    /**
     * 使用猪舍
     */
    private Integer hogringId;

    /**
     * 饲料用量
     */
    private BigDecimal feedAmount;

    /**
     * 管理人员id
     */
    private Long feeder;

    /**
     * 管理员类别(1.总管理员;2.猪舍管理员;3.猪只信息管理员;4.饲料管理员;5.进出库管理员)
     */
    private Integer feeder_type;

}