package com.maxqiu.mall.product.rquest;

import com.maxqiu.mall.common.request.BasePageRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品品牌 分页
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BrandPageRequest extends BasePageRequest {
    /**
     * 品牌名称
     */
    private String name;
}
