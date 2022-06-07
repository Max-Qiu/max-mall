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

import com.maxqiu.mall.common.vaild.CreateValidGroup;
import com.maxqiu.mall.common.vaild.DeleteValidGroup;
import com.maxqiu.mall.common.vaild.UpdateValidGroup;
import com.maxqiu.mall.common.vo.Result;
import com.maxqiu.mall.product.entity.Brand;
import com.maxqiu.mall.product.entity.BrandCategoryRelation;
import com.maxqiu.mall.product.entity.Category;
import com.maxqiu.mall.product.rquest.BrandCategoryRelationFormRequest;
import com.maxqiu.mall.product.rquest.CategoryFormRequest;
import com.maxqiu.mall.product.service.BrandCategoryRelationService;
import com.maxqiu.mall.product.service.BrandService;
import com.maxqiu.mall.product.service.CategoryService;
import com.maxqiu.mall.product.vo.BrandNameVO;
import com.maxqiu.mall.product.vo.CategoryVO;

/**
 * 商品分类 前端控制器
 *
 * @author Max_Qiu
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandCategoryRelationService relationService;

    @Autowired
    private BrandService brandService;

    /**
     * 获取分类树
     */
    @GetMapping("tree")
    public Result<List<CategoryVO>> tree() {
        List<CategoryVO> tree = categoryService.treeVO();
        return Result.success(tree);
    }

    /**
     * 新增分类
     */
    @PostMapping("create")
    public Result<String> create(@RequestBody @Validated(value = CreateValidGroup.class) CategoryFormRequest formRequest) {
        if (formRequest.getParentId() != 0) {
            Category p = categoryService.getById(formRequest.getParentId());
            if (p.getLevel() >= 3) {
                return Result.fail("父ID不能为三级");
            }
        }
        return Result.byFlag(categoryService.create(formRequest));
    }

    /**
     * 修改分类
     */
    @PostMapping("update")
    public Result<String> update(@RequestBody @Validated(value = UpdateValidGroup.class) CategoryFormRequest formRequest) {
        Category current = categoryService.getById(formRequest.getId());
        if (current == null) {
            return Result.fail("当前分类ID不存在");
        }
        // 当隐藏菜单时
        if (!formRequest.getShowStatus()) {
            // 当前分类有商品在上架，不可以隐藏
            if (current.getProductCount() != 0) {
                return Result.fail("当前分类有商品在上架时，不可以隐藏");
            }
        }
        return Result.byFlag(categoryService.update(formRequest, current.getLevel() == 3 && !current.getName().equals(formRequest.getName())));
    }

    /**
     * 删除分类
     */
    @PostMapping("delete")
    public Result<String> delete(@RequestBody @Validated(value = DeleteValidGroup.class) CategoryFormRequest formRequest) {
        Category current = categoryService.getById(formRequest.getId());
        if (current == null) {
            return Result.fail("当前分类ID不存在");
        }
        // 当前分类有商品在上架时，不可以删除
        if (current.getProductCount() != 0) {
            return Result.fail("当前分类有商品在上架时，不可以删除");
        }
        // 可能存在子分类时
        if (current.getLevel() <= 2) {
            // 获取子分类
            List<Category> childList = categoryService.childListById(formRequest.getId());
            if (!childList.isEmpty()) {
                return Result.fail("存在子分类时，无法删除");
            }
        }
        // 校验品牌关联
        boolean exist = relationService.existByCategory(formRequest.getId());
        if (exist) {
            return Result.fail("存在品牌关联，无法删除");
        }
        // TODO 校验是否关联商品
        return Result.byFlag(categoryService.delete(formRequest.getId()));
    }

    /**
     * 获取关联的品牌列表
     */
    @GetMapping("brand-relation/list/{categoryId}")
    public Result<List<BrandNameVO>> brandRelationList(@PathVariable Integer categoryId) {
        List<BrandCategoryRelation> list = relationService.listBrandByCategoryId(categoryId);
        List<BrandNameVO> collect = list.stream().map(BrandNameVO::new).collect(Collectors.toList());
        return Result.success(collect);
    }

    /**
     * 创建品牌关联关系
     */
    @PostMapping("brand-relation/create")
    public Result<String> brandRelationCreate(@RequestBody BrandCategoryRelationFormRequest formRequest) {
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
     * 删除品牌关联关系
     */
    @PostMapping("brand-relation/delete")
    public Result<String> brandRelationDelete(@RequestBody BrandCategoryRelationFormRequest formRequest) {
        boolean exist = relationService.exist(formRequest.getBrandId(), formRequest.getCategoryId());
        if (!exist) {
            return Result.fail("关联关系不存在");
        }
        return Result.byFlag(relationService.delete(formRequest.getBrandId(), formRequest.getCategoryId()));
    }
}
