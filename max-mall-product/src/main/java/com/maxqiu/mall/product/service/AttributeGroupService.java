package com.maxqiu.mall.product.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maxqiu.mall.product.entity.AttributeGroup;
import com.maxqiu.mall.product.mapper.AttributeGroupMapper;
import com.maxqiu.mall.product.rquest.AttributeGroupFormRequest;

/**
 * 属性分组 服务类
 *
 * @author Max_Qiu
 */
@Service
public class AttributeGroupService extends ServiceImpl<AttributeGroupMapper, AttributeGroup> {
    /**
     * 根据分类ID获取属性分组
     */
    public Page<AttributeGroup> pageByCategoryId(Integer pageNumber, Integer pageSize, Integer categoryId) {
        Page<AttributeGroup> page = new Page<>(pageNumber, pageSize);
        LambdaQueryWrapper<AttributeGroup> wrapper = Wrappers.lambdaQuery();
        if (categoryId != null) {
            wrapper.eq(AttributeGroup::getCategoryId, categoryId);
        }
        return super.page(page, wrapper);
    }

    /**
     * 新增
     */
    public boolean create(AttributeGroupFormRequest formRequest) {
        AttributeGroup attributeGroup = new AttributeGroup();
        BeanUtils.copyProperties(formRequest, attributeGroup);
        return super.save(attributeGroup);
    }

    /**
     * 修改
     */
    public boolean update(AttributeGroupFormRequest formRequest) {
        AttributeGroup attributeGroup = new AttributeGroup();
        BeanUtils.copyProperties(formRequest, attributeGroup);
        return super.updateById(attributeGroup);
    }

    /**
     * 删除
     */
    public boolean delete(Integer id) {
        return super.removeById(id);
    }
}
