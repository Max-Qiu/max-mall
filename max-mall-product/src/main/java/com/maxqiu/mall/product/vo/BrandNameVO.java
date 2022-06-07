package com.maxqiu.mall.product.vo;

import com.maxqiu.mall.product.entity.BrandCategoryRelation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品品牌名称 VO
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BrandNameVO {
    /**
     * 品牌id
     */
    private Integer id;

    /**
     * 品牌名称
     */
    private String name;

    public BrandNameVO(BrandCategoryRelation relation) {
        this.id = relation.getBrandId();
        this.name = relation.getBrandName();
    }
}
