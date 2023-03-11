package com.hzzzzzy.project.model.dto.hogring;
import lombok.Data;

import java.util.List;

/**
 * 获取猪舍内肉猪id列表
 *
 * @author hzzzzzy
 */
@Data
public class PigIdListGetRequest {

    /**
     * 猪舍id
     */
    private Integer hogringId;

    /**
     * 猪只id
     */
    private List<Integer> pigId;
}
