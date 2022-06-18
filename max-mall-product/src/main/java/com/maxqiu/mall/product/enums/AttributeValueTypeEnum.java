package com.maxqiu.mall.product.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 属性值类型枚举
 *
 * @author Max_Qiu
 */
@Getter
@AllArgsConstructor
public enum AttributeValueTypeEnum {
    BASE(1, "单选"),

    SALE(2, "多选");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;
}
