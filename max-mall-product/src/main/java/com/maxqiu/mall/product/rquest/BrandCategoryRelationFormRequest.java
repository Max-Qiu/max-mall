package com.maxqiu.mall.product.rquest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 品牌分类关联 表单
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BrandCategoryRelationFormRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @NotNull
    @Positive
    private Integer brandId;

    /**
     * 分类id
     */
    @NotNull
    @Positive
    private Integer categoryId;
}
