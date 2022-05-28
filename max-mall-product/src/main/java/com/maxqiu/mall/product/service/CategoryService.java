package com.maxqiu.mall.product.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maxqiu.mall.product.entity.Category;
import com.maxqiu.mall.product.mapper.CategoryMapper;
import com.maxqiu.mall.product.rquest.CategoryRequest;
import com.maxqiu.mall.product.vo.CategoryVO;

/**
 * 商品分类 服务类
 *
 * @author Max_Qiu
 */
@Service
@CacheConfig(cacheNames = "CategoryService", keyGenerator = "cacheKeyGenerator")
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {
    /**
     * 获取树形结构
     */
    @Cacheable
    public List<CategoryVO> treeVO() {
        List<Category> list = super.list();
        return listToTreeVO(0, list);
    }

    /**
     * 递归查找所有菜单的子菜单
     */
    private List<CategoryVO> listToTreeVO(Integer pid, List<Category> all) {
        return all.stream()
            // 根据父ID过滤
            .filter(category -> category.getParentId().equals(pid))
            // 转换为VO
            .map(e -> new CategoryVO(e, listToTreeVO(e.getId(), all)))
            // 排序
            .sorted(Comparator.comparingInt(CategoryVO::getSort))
            // 收集
            .collect(Collectors.toList());
    }

    /**
     * 根据ID获取当前分类
     */
    @Cacheable
    public Category getById(Integer id) {
        return super.getById(id);
    }

    /**
     * 根据ID获取子分类
     */
    @Cacheable
    public List<Category> childListById(Integer id) {
        LambdaQueryWrapper<Category> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Category::getParentId, id);
        return super.list(wrapper);
    }

    /**
     * 新增
     */
    @CacheEvict(allEntries = true)
    public boolean save(CategoryRequest request) {
        Category category = new Category();
        BeanUtils.copyProperties(request, category);
        if (request.getParentId() == 0) {
            // 父ID为0，则层级为一级菜单
            category.setLevel(1);
        } else {
            Category p = getById(request.getParentId());
            category.setLevel(p.getLevel() + 1);
        }
        return super.save(category);
    }

    /**
     * 修改
     */
    @CacheEvict(allEntries = true)
    public boolean update(CategoryRequest request) {
        Category category = new Category();
        BeanUtils.copyProperties(request, category);
        return super.updateById(category);
    }

    /**
     * 删除
     */
    @CacheEvict(allEntries = true)
    public boolean removeById(Integer id) {
        return super.removeById(id);
    }
}
