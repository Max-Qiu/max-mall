package com.maxqiu.mall.product.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maxqiu.mall.product.entity.Brand;
import com.maxqiu.mall.product.entity.BrandCategoryRelation;
import com.maxqiu.mall.product.entity.Category;
import com.maxqiu.mall.product.mapper.BrandCategoryRelationMapper;

/**
 * 品牌分类关联 服务类
 *
 * @author Max_Qiu
 */
@Service
@CacheConfig(cacheNames = "BrandCategoryRelationService", keyGenerator = "cacheKeyGenerator")
public class BrandCategoryRelationService extends ServiceImpl<BrandCategoryRelationMapper, BrandCategoryRelation> {
    /**
     * 根据品牌ID获取分组列表
     */
    @Cacheable
    public List<BrandCategoryRelation> listCategoryByBrandId(Integer brandId) {
        LambdaQueryWrapper<BrandCategoryRelation> wrapper = Wrappers.lambdaQuery();
        wrapper.select(BrandCategoryRelation::getCategoryId, BrandCategoryRelation::getCategoryName);
        wrapper.eq(BrandCategoryRelation::getBrandId, brandId);
        return super.list(wrapper);
    }

    /**
     * 根据分类ID获取品牌列表
     */
    @Cacheable
    public List<BrandCategoryRelation> listBrandByCategoryId(Integer categoryId) {
        LambdaQueryWrapper<BrandCategoryRelation> wrapper = Wrappers.lambdaQuery();
        wrapper.select(BrandCategoryRelation::getBrandId, BrandCategoryRelation::getBrandName);
        wrapper.eq(BrandCategoryRelation::getCategoryId, categoryId);
        return super.list(wrapper);
    }

    /**
     * 查询是否已存在
     */
    public boolean exist(Integer brandId, Integer categoryId) {
        LambdaQueryWrapper<BrandCategoryRelation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(BrandCategoryRelation::getBrandId, brandId);
        wrapper.eq(BrandCategoryRelation::getCategoryId, categoryId);
        return super.count(wrapper) > 0;
    }

    /**
     * 查询是否已存在
     */
    public boolean existByBrandId(Integer brandId) {
        LambdaQueryWrapper<BrandCategoryRelation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(BrandCategoryRelation::getCategoryId, brandId);
        return super.count(wrapper) > 0;
    }

    /**
     * 查询是否已存在
     */
    public boolean existByCategory(Integer categoryId) {
        LambdaQueryWrapper<BrandCategoryRelation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(BrandCategoryRelation::getBrandId, categoryId);
        return super.count(wrapper) > 0;
    }

    /**
     * 创建
     */
    @CacheEvict
    public boolean create(Brand brand, Category category) {
        BrandCategoryRelation relation = new BrandCategoryRelation();
        relation.setBrandId(brand.getId());
        relation.setBrandName(brand.getName());
        relation.setCategoryId(category.getId());
        relation.setCategoryName(category.getName());
        return super.save(relation);
    }

    /**
     * 删除
     */
    @CacheEvict
    public boolean delete(Integer brandId, Integer categoryId) {
        LambdaQueryWrapper<BrandCategoryRelation> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(BrandCategoryRelation::getBrandId, brandId);
        wrapper.eq(BrandCategoryRelation::getCategoryId, categoryId);
        return super.remove(wrapper);
    }

    /**
     * 刷新品牌名称
     */
    @CacheEvict
    public void updateBrandName(Integer brandId, String brandName) {
        LambdaUpdateWrapper<BrandCategoryRelation> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(BrandCategoryRelation::getBrandName, brandName);
        wrapper.eq(BrandCategoryRelation::getBrandId, brandId);
        super.update(wrapper);
    }

    /**
     * 刷新分类名称
     */
    @CacheEvict
    public void updateCategoryName(Integer categoryId, String categoryName) {
        LambdaUpdateWrapper<BrandCategoryRelation> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(BrandCategoryRelation::getCategoryName, categoryName);
        wrapper.eq(BrandCategoryRelation::getCategoryId, categoryId);
        super.update(wrapper);
    }
}
