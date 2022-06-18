package com.maxqiu.mall.product.rquest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.maxqiu.mall.common.vaild.CreateValidGroup;
import com.maxqiu.mall.common.vaild.DeleteValidGroup;
import com.maxqiu.mall.common.vaild.UpdateValidGroup;
import com.maxqiu.mall.product.enums.AttributeValueTypeEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品属性 基本属性 表单
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AttributeSaleFormRequest {
    /**
     * 属性id
     */
    @Null(groups = CreateValidGroup.class)
    @NotNull(groups = {UpdateValidGroup.class, DeleteValidGroup.class})
    private Integer id;

    /**
     * 属性名
     */
    @NotNull(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String name;

    /**
     * 值类型 1单选 2多选
     */
    @NotNull(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private AttributeValueTypeEnum valueType;

    /**
     * 可选值列表(用逗号分隔)
     */
    @Valid
    private List<@NotBlank(groups = {CreateValidGroup.class, UpdateValidGroup.class}) String> values;

    /**
     * 状态
     */
    @NotNull(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Boolean status;

    /**
     * 所属分类ID
     */
    @NotNull(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Integer categoryId;
}
