package com.maxqiu.mall.product.rquest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import com.maxqiu.mall.common.vaild.CreateValidGroup;
import com.maxqiu.mall.common.vaild.DeleteValidGroup;
import com.maxqiu.mall.common.vaild.UpdateValidGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品品牌
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BrandFormRequest {
    /**
     * 品牌id
     */
    @Null(groups = CreateValidGroup.class)
    @NotNull(groups = {UpdateValidGroup.class, DeleteValidGroup.class})
    private Integer id;

    /**
     * 名称
     */
    @NotNull(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String name;

    /**
     * 标识
     */
    @URL(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    private String logo;

    /**
     * 首字母
     */
    @NotBlank(groups = {CreateValidGroup.class, UpdateValidGroup.class})
    @Pattern(regexp = "^[A-Z]$", groups = {CreateValidGroup.class, UpdateValidGroup.class}, message = "只支持一个英文大写字母")
    private String initial;
}
