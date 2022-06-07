package com.maxqiu.mall.product.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maxqiu.mall.common.vaild.CreateValidGroup;
import com.maxqiu.mall.common.vaild.DeleteValidGroup;
import com.maxqiu.mall.common.vaild.UpdateValidGroup;
import com.maxqiu.mall.common.vo.PageResult;
import com.maxqiu.mall.common.vo.Result;
import com.maxqiu.mall.product.entity.Brand;
import com.maxqiu.mall.product.entity.BrandCategoryRelation;
import com.maxqiu.mall.product.entity.Category;
import com.maxqiu.mall.product.rquest.BrandCategoryRelationFormRequest;
import com.maxqiu.mall.product.rquest.BrandFormRequest;
import com.maxqiu.mall.product.rquest.BrandPageRequest;
import com.maxqiu.mall.product.service.BrandCategoryRelationService;
import com.maxqiu.mall.product.service.BrandService;
import com.maxqiu.mall.product.service.CategoryService;
import com.maxqiu.mall.product.vo.BrandVO;
import com.maxqiu.mall.product.vo.CategoryNameVO;

/**
 * 商品品牌 前端控制器
 *
 * @author Max_Qiu
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandCategoryRelationService relationService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据品牌名称分页
     */
    @GetMapping("page")
    public Result<PageResult<BrandVO>> page(BrandPageRequest pageRequest) {
        Page<Brand> page = brandService.page(pageRequest);
        List<BrandVO> collect = page.getRecords().stream().map(BrandVO::new).collect(Collectors.toList());
        return Result.success(new PageResult<>(collect, page));
    }

    /**
     * 新增品牌
     */
    @PostMapping("create")
    public Result<String> create(@RequestBody @Validated(value = CreateValidGroup.class) BrandFormRequest formRequest) {
        return Result.byFlag(brandService.create(formRequest));
    }

    /**
     * 修改品牌
     */
    @PostMapping("update")
    public Result<String> update(@RequestBody @Validated(value = UpdateValidGroup.class) BrandFormRequest formRequest) {
        Brand current = brandService.getById(formRequest.getId());
        if (current == null) {
            return Result.fail("品牌ID不存在");
        }
        return Result.byFlag(brandService.update(formRequest, !current.getName().equals(formRequest.getName())));
    }

    /**
     * 删除品牌
     */
    @PostMapping("delete")
    public Result<String> delete(@RequestBody @Validated(value = DeleteValidGroup.class) BrandFormRequest formRequest) {
        Brand current = brandService.getById(formRequest.getId());
        if (current == null) {
            return Result.fail("品牌ID不存在");
        }
        boolean exist = relationService.existByBrandId(formRequest.getId());
        if (exist) {
            return Result.fail("存在分类关联，无法删除");
        }
        // TODO 校验是否关联商品
        return Result.byFlag(brandService.delete(formRequest.getId()));
    }

    /**
     * 获取关联的分类列表
     */
    @GetMapping("category-relation/list/{brandId}")
    public Result<List<CategoryNameVO>> categoryRelationList(@PathVariable Integer brandId) {
        List<BrandCategoryRelation> list = relationService.listCategoryByBrandId(brandId);
        List<CategoryNameVO> collect = list.stream().map(CategoryNameVO::new).collect(Collectors.toList());
        return Result.success(collect);
    }

    /**
     * 创建分类关联关系
     */
    @PostMapping("category-relation/create")
    public Result<String> categoryRelationCreate(@RequestBody BrandCategoryRelationFormRequest formRequest) {
        Brand brand = brandService.getById(formRequest.getBrandId());
        if (brand == null) {
            return Result.fail("品牌ID不存在");
        }
        Category category = categoryService.getById(formRequest.getCategoryId());
        if (category == null) {
            return Result.fail("分类ID不存在");
        }
        if (category.getLevel() != 3) {
            return Result.fail("只能关联三级分类ID");
        }
        boolean exist = relationService.exist(formRequest.getBrandId(), formRequest.getCategoryId());
        if (exist) {
            return Result.fail("关联关系已存在");
        }
        return Result.byFlag(relationService.create(brand, category));
    }

    /**
     * 删除分类关联关系
     */
    @PostMapping("category-relation/delete")
    public Result<String> categoryRelationDelete(@RequestBody BrandCategoryRelationFormRequest formRequest) {
        boolean exist = relationService.exist(formRequest.getBrandId(), formRequest.getCategoryId());
        if (!exist) {
            return Result.fail("关联关系不存在");
        }
        return Result.byFlag(relationService.delete(formRequest.getBrandId(), formRequest.getCategoryId()));
    }
}
