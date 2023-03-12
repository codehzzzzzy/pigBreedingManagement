package com.hzzzzzy.project.constant;

/**
 * 用户常量
 *
 * @author hzzzzzy
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 总管理员
     *
     * @return
     */
    Integer GENERAL_ADMIN = 1;

    /**
     * 猪舍管理员
     *
     * @return
     */
    Integer PIGSTY_ADMIN = 2;

    /**
     * 肉猪信息管理员
     *
     * @return
     */
    Integer PIG_ADMIN = 3;


    /**
     * 进出库管理员
     *
     * @return
     */
    Integer PIG_INVENTORY_ADMIN = 4;


    /**
     * 饲料管理员
     *
     * @return
     */
    Integer FODDER_ADMIN = 5;
}
