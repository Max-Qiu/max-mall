package com.maxqiu.mall.product.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maxqiu.mall.product.entity.Attribute;
import com.maxqiu.mall.product.enums.AttributeTypeEnum;
import com.maxqiu.mall.product.mapper.AttributeMapper;
import com.maxqiu.mall.product.rquest.AttributeBaseFormRequest;
import com.maxqiu.mall.product.rquest.AttributeSaleFormRequest;

/**
 * 商品属性 服务类
 *
 * @author Max_Qiu
 */
@Service
public class AttributeService extends ServiceImpl<AttributeMapper, Attribute> {
    /**
     * 根据分类ID和类型分页获取属性
     */
    public Page<Attribute> pageByCategoryIdAndType(AttributeTypeEnum type, Integer pageNumber, Integer pageSize, Integer categoryId) {
        Page<Attribute> page = new Page<>(pageNumber, pageSize);
        LambdaQueryWrapper<Attribute> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Attribute::getType, type);
        if (categoryId != null) {
            wrapper.eq(Attribute::getCategoryId, categoryId);
        }
        return super.page(page, wrapper);
    }

    /**
     * 新增 基本
     */
    public boolean create(AttributeBaseFormRequest formRequest) {
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(formRequest, attribute);
        attribute.setType(AttributeTypeEnum.BASE);
        return super.save(attribute);
    }

    /**
     * 新增 销售
     */
    public boolean create(AttributeSaleFormRequest formRequest) {
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(formRequest, attribute);
        attribute.setType(AttributeTypeEnum.SALE);
        return super.save(attribute);
    }

    /**
     * 修改 基本
     */
    public boolean update(AttributeBaseFormRequest formRequest) {
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(formRequest, attribute);
        return super.updateById(attribute);
    }

    /**
     * 修改 销售
     */
    public boolean update(AttributeSaleFormRequest formRequest) {
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(formRequest, attribute);
        return super.updateById(attribute);
    }

    /**
     * 删除
     */
    public boolean delete(Integer id) {
        return super.removeById(id);
    }
}
