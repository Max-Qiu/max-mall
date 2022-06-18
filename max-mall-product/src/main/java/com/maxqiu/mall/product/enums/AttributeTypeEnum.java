package com.maxqiu.mall.product.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 属性类型枚举
 *
 * @author Max_Qiu
 */
@Getter
@AllArgsConstructor
public enum AttributeTypeEnum {
    BASE(1, "基本属性"),

    SALE(2, "销售属性");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;
}
