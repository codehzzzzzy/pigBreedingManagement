package com.hzzzzzy.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 饲料管理
 * @TableName feed_management
 */
@TableName(value ="feed_management")
@Data
public class FeedManagement implements Serializable {
    /**
     * 饲料id 主键
     */
    @TableId(type = IdType.AUTO)
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
        FeedManagement other = (FeedManagement) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFeedType() == null ? other.getFeedType() == null : this.getFeedType().equals(other.getFeedType()))
            && (this.getHogringId() == null ? other.getHogringId() == null : this.getHogringId().equals(other.getHogringId()))
            && (this.getFeedAmount() == null ? other.getFeedAmount() == null : this.getFeedAmount().equals(other.getFeedAmount()))
            && (this.getFeeder() == null ? other.getFeeder() == null : this.getFeeder().equals(other.getFeeder()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFeedType() == null) ? 0 : getFeedType().hashCode());
        result = prime * result + ((getHogringId() == null) ? 0 : getHogringId().hashCode());
        result = prime * result + ((getFeedAmount() == null) ? 0 : getFeedAmount().hashCode());
        result = prime * result + ((getFeeder() == null) ? 0 : getFeeder().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", feedType=").append(feedType);
        sb.append(", hogringId=").append(hogringId);
        sb.append(", feedAmount=").append(feedAmount);
        sb.append(", feeder=").append(feeder);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}