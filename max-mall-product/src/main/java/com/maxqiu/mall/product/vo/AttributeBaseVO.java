package com.maxqiu.mall.product.vo;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.maxqiu.mall.product.entity.Attribute;
import com.maxqiu.mall.product.enums.AttributeValueTypeEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品属性
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AttributeBaseVO {
    /**
     * 属性id
     */
    private Integer id;

    /**
     * 属性名
     */
    private String name;

    /**
     * 值类型 1单选 2多选
     */
    private AttributeValueTypeEnum valueType;

    /**
     * 可选值列表
     */
    private List<String> values;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 所属分类ID
     */
    private Integer categoryId;

    /**
     * 所属分类名称
     */
    private String categoryName;

    /**
     * 属性分组ID
     */
    private Integer groupId;

    /**
     * 属性分组名称
     */
    private String groupName;

    public AttributeBaseVO(Attribute attribute, String categoryName, String groupName) {
        BeanUtils.copyProperties(attribute, this);
        this.categoryName = categoryName;
        this.groupName = groupName;
    }
}
