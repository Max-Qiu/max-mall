package com.maxqiu.mall.product.rquest;

import com.maxqiu.mall.common.request.BasePageRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 属性分组分页请求
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AttributeGroupPageRequest extends BasePageRequest {
    /**
     * 分类ID
     */
    private Integer categoryId;
}
