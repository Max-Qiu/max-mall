package com.maxqiu.mall.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxqiu.mall.common.vaild.AddValidGroup;
import com.maxqiu.mall.common.vaild.DeleteValidGroup;
import com.maxqiu.mall.common.vaild.UpdateValidGroup;
import com.maxqiu.mall.common.vo.Result;
import com.maxqiu.mall.product.entity.Category;
import com.maxqiu.mall.product.rquest.CategoryRequest;
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
    @PostMapping("save")
    public Result<String> save(@RequestBody @Validated(value = AddValidGroup.class) CategoryRequest request) {
        if (request.getParentId() != 0) {
            Category p = categoryService.getById(request.getParentId());
            if (p.getLevel() >= 3) {
                return Result.fail("父ID不能为三级");
            }
        }
        return Result.byFlag(categoryService.save(request));
    }

    /**
     * 修改分类
     */
    @PostMapping("update")
    public Result<String> update(@RequestBody @Validated(value = UpdateValidGroup.class) CategoryRequest request) {
        // 当隐藏菜单时
        if (!request.getShowStatus()) {
            // 当前分类有商品在上架，不可以隐藏
            Category current = categoryService.getById(request.getId());
            if (current.getProductCount() != 0) {
                return Result.fail("当前分类有商品在上架时，不可以隐藏");
            }
        }
        return Result.byFlag(categoryService.update(request));
    }

    /**
     * 删除分类
     */
    @PostMapping("remove")
    public Result<String> remove(@RequestBody @Validated(value = DeleteValidGroup.class) CategoryRequest request) {
        // 当前分类有商品在上架时，不可以删除
        Category current = categoryService.getById(request.getId());
        if (current.getProductCount() != 0) {
            return Result.fail("当前分类有商品在上架时，不可以删除");
        }
        // 可能存在子分类时
        if (current.getLevel() <= 2) {
            // 获取子分类
            List<Category> childList = categoryService.childListById(request.getId());
            if (!childList.isEmpty()) {
                return Result.fail("存在子分类时，无法删除");
            }
        }
        return Result.byFlag(categoryService.removeById(request.getId()));
    }
}
