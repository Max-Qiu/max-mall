package com.maxqiu.mall.product.vo;

import com.maxqiu.mall.product.entity.BrandCategoryRelation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品分类名称 VO
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryNameVO {
    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    public CategoryNameVO(BrandCategoryRelation relation) {
        this.id = relation.getCategoryId();
        this.name = relation.getCategoryName();
    }
}
