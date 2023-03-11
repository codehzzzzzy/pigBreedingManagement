package com.hzzzzzy.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 猪只进出库管理
 * @TableName pig_in_out
 */
@TableName(value ="pig_in_out")
@Data
public class PigInOut implements Serializable {
    /**
     * 进出记录id 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 猪只id
     */
    private Integer pigId;

    /**
     * 进入猪舍id
     */
    private Integer hogringId;

    /**
     * 进入时间
     */
    private Date inTime;

    /**
     * 出库时间
     */
    private Date outTime;

    /**
     * 猪只来源
     */
    private String source;

    /**
     * 猪只去向
     */
    private String destination;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PigInOut other = (PigInOut) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPigId() == null ? other.getPigId() == null : this.getPigId().equals(other.getPigId()))
            && (this.getHogringId() == null ? other.getHogringId() == null : this.getHogringId().equals(other.getHogringId()))
            && (this.getInTime() == null ? other.getInTime() == null : this.getInTime().equals(other.getInTime()))
            && (this.getOutTime() == null ? other.getOutTime() == null : this.getOutTime().equals(other.getOutTime()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getDestination() == null ? other.getDestination() == null : this.getDestination().equals(other.getDestination()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPigId() == null) ? 0 : getPigId().hashCode());
        result = prime * result + ((getHogringId() == null) ? 0 : getHogringId().hashCode());
        result = prime * result + ((getInTime() == null) ? 0 : getInTime().hashCode());
        result = prime * result + ((getOutTime() == null) ? 0 : getOutTime().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getDestination() == null) ? 0 : getDestination().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pigId=").append(pigId);
        sb.append(", hogringId=").append(hogringId);
        sb.append(", inTime=").append(inTime);
        sb.append(", outTime=").append(outTime);
        sb.append(", source=").append(source);
        sb.append(", destination=").append(destination);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}