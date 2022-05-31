package com.maxqiu.mall.product.rquest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.URL;

import com.maxqiu.mall.common.vaild.AddValidGroup;
import com.maxqiu.mall.common.vaild.DeleteValidGroup;
import com.maxqiu.mall.common.vaild.UpdateValidGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品分类
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryRequest {
    /**
     * 分类id
     */
    @Null(groups = AddValidGroup.class)
    @NotNull(groups = {UpdateValidGroup.class, DeleteValidGroup.class})
    private Integer id;

    /**
     * 分类名称
     */
    @NotBlank(groups = {AddValidGroup.class, UpdateValidGroup.class})
    private String name;

    /**
     * 父分类id
     *
     * 分类一般不会移动，所以修改时，不支持修改父分类ID
     */
    @NotNull(groups = AddValidGroup.class)
    @Null(groups = UpdateValidGroup.class)
    private Integer parentId;

    /**
     * 是否显示
     */
    @NotNull(groups = {AddValidGroup.class, UpdateValidGroup.class})
    private Boolean showStatus;

    /**
     * 排序
     */
    @NotNull(groups = {AddValidGroup.class, UpdateValidGroup.class})
    @Min(value = 0, groups = {AddValidGroup.class, UpdateValidGroup.class})
    @Max(value = 999, groups = {AddValidGroup.class, UpdateValidGroup.class})
    private Integer sort;

    /**
     * 图标地址
     */
    @URL(groups = {AddValidGroup.class, UpdateValidGroup.class})
    private String icon;
}
