package com.maxqiu.mall.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxqiu.mall.common.vo.Result;
import com.maxqiu.mall.product.service.CategoryService;
import com.maxqiu.mall.product.vo.CategoryVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 商品分类 前端控制器
 *
 * @author Max_Qiu
 */
@RestController
@RequestMapping("/category")
@Slf4j
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
}
