package com.maxqiu.mall.product.vo;

import org.springframework.beans.BeanUtils;

import com.maxqiu.mall.product.entity.AttributeGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 属性分组 VO
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AttributeGroupVO {
    /**
     * 属性分组ID
     */
    private Integer id;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 描述
     */
    private String description;

    /**
     * 所属分类ID
     */
    private Integer categoryId;

    /**
     * 所属分类名称
     */
    private String categoryName;

    public AttributeGroupVO(AttributeGroup attributeGroup, String categoryName) {
        BeanUtils.copyProperties(attributeGroup, this);
        this.categoryName = categoryName;
    }
}
