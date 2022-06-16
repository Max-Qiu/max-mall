package com.maxqiu.mall.product.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 商品分类
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@TableName("mm_category")
public class Category extends Model<Category> {
    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 父分类id
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 层级
     */
    @TableField("`level`")
    private Integer level;

    /**
     * 状态
     */
    @TableField("`status`")
    private Boolean status;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 图标地址
     */
    @TableField("icon")
    private String icon;

    /**
     * 商品数量
     */
    @TableField("product_count")
    private Integer productCount;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
