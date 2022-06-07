package com.maxqiu.mall.product.rquest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.maxqiu.mall.common.vaild.CreateValidGroup;
import com.maxqiu.mall.common.vaild.DeleteValidGroup;
import com.maxqiu.mall.common.vaild.UpdateValidGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 属性分组 表单
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AttributeGroupFormRequest {
    /**
     * 属性分组ID
     */
    @Null(groups = CreateValidGroup.class)
    @NotNull(groups = {UpdateValidGroup.class, DeleteValidGroup.class})
    private Integer id;

    /**
     * 分组名称
     */
    @NotBlank(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String name;

    /**
     * 排序
     */
    @NotNull(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Integer sort;

    /**
     * 描述
     */
    private String description;

    /**
     * 所属分类ID
     */
    @NotNull(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private Integer categoryId;
}
