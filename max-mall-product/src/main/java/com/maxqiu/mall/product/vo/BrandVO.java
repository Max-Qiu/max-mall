package com.maxqiu.mall.product.vo;

import org.springframework.beans.BeanUtils;

import com.maxqiu.mall.product.entity.Brand;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品品牌 VO
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BrandVO {
    /**
     * 品牌id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 标识
     */
    private String logo;

    /**
     * 首字母
     */
    private String initial;

    /**
     * 状态
     */
    private Boolean status;

    public BrandVO(Brand brand) {
        BeanUtils.copyProperties(brand, this);
    }
}
