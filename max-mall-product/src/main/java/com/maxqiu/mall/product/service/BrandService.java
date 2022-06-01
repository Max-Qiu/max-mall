package com.maxqiu.mall.product.service;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maxqiu.mall.product.entity.Brand;
import com.maxqiu.mall.product.mapper.BrandMapper;
import com.maxqiu.mall.product.rquest.BrandFormRequest;
import com.maxqiu.mall.product.rquest.BrandPageRequest;

/**
 * 商品品牌 服务类
 *
 * @author Max_Qiu
 */
@Service
@CacheConfig(cacheNames = "BrandService", keyGenerator = "cacheKeyGenerator")
public class BrandService extends ServiceImpl<BrandMapper, Brand> {
    /**
     * 根据品牌名称分页获取列表
     */
    public Page<Brand> page(BrandPageRequest pageRequest) {
        Page<Brand> page = new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.hasText(pageRequest.getName())) {
            wrapper.like(Brand::getName, pageRequest.getName());
        }
        return page(page, wrapper);
    }

    /**
     * 根据ID获取品牌
     */
    @Cacheable
    public Brand getById(Integer id) {
        return super.getById(id);
    }

    /**
     * 新增
     */
    @CacheEvict(allEntries = true)
    public boolean create(BrandFormRequest formRequest) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(formRequest, brand);
        return super.save(brand);
    }

    /**
     * 修改
     */
    @CacheEvict(allEntries = true)
    public boolean update(BrandFormRequest formRequest) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(formRequest, brand);
        return super.updateById(brand);
    }

    /**
     * 删除
     */
    @CacheEvict(allEntries = true)
    public boolean delete(Integer id) {
        return super.removeById(id);
    }
}
