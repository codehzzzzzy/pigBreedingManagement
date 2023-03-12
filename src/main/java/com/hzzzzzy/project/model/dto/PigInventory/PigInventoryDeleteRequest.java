package com.hzzzzzy.project.model.dto.PigInventory;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 肉猪进出库删除信息请求
 *
 * @author hzzzzzy
 */
@Data
public class PigInventoryDeleteRequest {
    /**
     * 进出记录id
     */
    private Integer id;
}
