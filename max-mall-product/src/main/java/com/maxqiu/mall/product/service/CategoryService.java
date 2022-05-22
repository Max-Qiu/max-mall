package com.maxqiu.mall.product.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maxqiu.mall.product.entity.Category;
import com.maxqiu.mall.product.mapper.CategoryMapper;
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
        List<Category> list = list();
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
}
