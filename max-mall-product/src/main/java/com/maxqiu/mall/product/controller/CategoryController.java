package com.maxqiu.mall.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxqiu.mall.common.vaild.CreateValidGroup;
import com.maxqiu.mall.common.vaild.DeleteValidGroup;
import com.maxqiu.mall.common.vaild.UpdateValidGroup;
import com.maxqiu.mall.common.vo.Result;
import com.maxqiu.mall.product.entity.Category;
import com.maxqiu.mall.product.rquest.CategoryFormRequest;
import com.maxqiu.mall.product.service.CategoryService;
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
        return Result.byFlag(categoryService.update(formRequest));
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
        return Result.byFlag(categoryService.delete(formRequest.getId()));
    }
}
