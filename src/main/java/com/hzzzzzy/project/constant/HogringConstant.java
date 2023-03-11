package com.hzzzzzy.project.constant;

import java.math.BigDecimal;

/**
 * 猪舍常量
 *
 * @author hzzzzzy
 */
public interface HogringConstant {

    /**
     * 猪舍状态-干净
     */
    Integer CLEAN = 0;

    /**
     * 猪舍状态-脏乱
     */
    Integer DIRTY = 1;

    /**
     * 辅助判断
     */
    BigDecimal ASSIST_IN_JUDGMENT =  new BigDecimal("0.0");
}
