package com.maxqiu.mall.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maxqiu.mall.product.entity.Brand;
import com.maxqiu.mall.product.mapper.BrandMapper;
import com.maxqiu.mall.product.rquest.BrandRequest;
import com.maxqiu.mall.product.vo.BrandVO;

/**
 * 商品品牌 服务类
 *
 * @author Max_Qiu
 */
@Service
@CacheConfig(cacheNames = "BrandService", keyGenerator = "cacheKeyGenerator")
public class BrandService extends ServiceImpl<BrandMapper, Brand> {
    /**
     * 获取列表
     */
    @Cacheable
    public List<BrandVO> listVO() {
        return super.list().stream().map(BrandVO::new).collect(Collectors.toList());
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
    public boolean save(BrandRequest request) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(request, brand);
        return super.save(brand);
    }

    /**
     * 修改
     */
    @CacheEvict(allEntries = true)
    public boolean update(BrandRequest request) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(request, brand);
        return super.updateById(brand);
    }

    /**
     * 删除
     */
    @CacheEvict(allEntries = true)
    public boolean removeById(Integer id) {
        return super.removeById(id);
    }
}
