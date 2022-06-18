package com.maxqiu.mall.common.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Fastjson2 实现 JSON 字段类型处理器
 *
 * @author Max_Qiu
 */
@Slf4j
@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class Fastjson2TypeHandler extends AbstractJsonTypeHandler<Object> {
    private final Class<?> type;

    public Fastjson2TypeHandler(Class<?> type) {
        if (log.isTraceEnabled()) {
            log.trace("FastjsonTypeHandler(" + type + ")");
        }
        Assert.notNull(type, "Type argument cannot be null");
        this.type = type;
    }

    @Override
    protected Object parse(String json) {
        return JSON.parseObject(json, type);
    }

    @Override
    protected String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
