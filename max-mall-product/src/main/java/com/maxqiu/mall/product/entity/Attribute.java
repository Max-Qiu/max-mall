package com.maxqiu.mall.product.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.maxqiu.mall.common.handler.Fastjson2TypeHandler;
import com.maxqiu.mall.product.enums.AttributeTypeEnum;
import com.maxqiu.mall.product.enums.AttributeValueTypeEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 商品属性
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@TableName(value = "mm_attribute", autoResultMap = true)
public class Attribute extends Model<Attribute> {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 属性名
     */
    @TableField("`name`")
    private String name;

    /**
     * 类型 1销售属性 2基本属性
     */
    @TableField(value = "`type`")
    private AttributeTypeEnum type;

    /**
     * 值类型 1单选 2多选
     */
    @TableField(value = "value_type")
    private AttributeValueTypeEnum valueType;

    /**
     * 可选值列表
     */
    @TableField(value = "`values`", typeHandler = Fastjson2TypeHandler.class)
    private List<String> values;

    /**
     * 状态
     */
    @TableField("`status`")
    private Boolean status;

    /**
     * 所属分类ID
     */
    @TableField("category_id")
    private Integer categoryId;

    /**
     * 属性分组ID
     */
    @TableField("group_id")
    private Integer groupId;

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
