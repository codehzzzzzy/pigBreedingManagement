package com.hzzzzzy.project.constant;

import java.math.BigDecimal;

/**
 * 肉猪常量
 *
 * @author hzzzzzy
 */
public interface PigConstant {
    /**
     * 未出库
     */
    Integer NOT_OUT_OF_STOCK = 0;


    /**
     * 已出库
     */
    Integer OUT_OF_STOCK = 1;


    /**
     * 不健康
     */
    Integer UNHEALTHY = 0;


    /**
     * 亚健康
     */
    Integer SUB_HEALTHY = 1;


    /**
     * 健康
     */
    Integer HEALTHY = 2;


    /**
     * 雄性
     */
    Integer MALE = 1;


    /**
     * 雌性
     */
    Integer FEMALE = 0;


    /**
     * 辅助判断
     */
    BigDecimal ASSIST_IN_JUDGMENT =  new BigDecimal("0.0");
}
