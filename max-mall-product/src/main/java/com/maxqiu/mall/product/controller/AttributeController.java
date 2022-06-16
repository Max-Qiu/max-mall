package com.maxqiu.mall.product.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.maxqiu.mall.product.entity.AttributeGroup;
import com.maxqiu.mall.product.entity.Category;
import com.maxqiu.mall.product.rquest.AttributeGroupFormRequest;
import com.maxqiu.mall.product.rquest.AttributeGroupPageRequest;
import com.maxqiu.mall.product.service.AttributeGroupService;
import com.maxqiu.mall.product.service.CategoryService;
import com.maxqiu.mall.product.vo.AttributeGroupVO;

/**
 * 属性 前端控制器
 *
 * @author Max_Qiu
 */
@RestController
@RequestMapping("/attribute")
public class AttributeController {
    @Autowired
    private AttributeGroupService attributeGroupService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据分类ID分页获取属性分组列表
     */
    @GetMapping("group/page")
    public Result<PageResult<AttributeGroupVO>> groupPage(AttributeGroupPageRequest pageRequest) {
        Page<AttributeGroup> page = attributeGroupService.pageByCategoryId(pageRequest);
        List<AttributeGroupVO> collect = page.getRecords().stream()
            .map(e -> new AttributeGroupVO(e, categoryService.getById(e.getCategoryId()).getName())).collect(Collectors.toList());
        return Result.success(new PageResult<>(collect, page));
    }

    /**
     * 新增属性分组
     */
    @PostMapping("group/create")
    public Result<String> groupCreate(@RequestBody @Validated(value = CreateValidGroup.class) AttributeGroupFormRequest formRequest) {
        Category category = categoryService.getById(formRequest.getCategoryId());
        // 检查分类ID
        if (category == null) {
            return Result.fail("分类ID不存在");
        }
        if (category.getLevel() != 3) {
            return Result.fail("仅支持三级分类ID");
        }
        return Result.byFlag(attributeGroupService.create(formRequest));
    }

    /**
     * 修改属性分组
     */
    @PostMapping("group/update")
    public Result<String> groupUpdate(@RequestBody @Validated(value = UpdateValidGroup.class) AttributeGroupFormRequest formRequest) {
        AttributeGroup byId = attributeGroupService.getById(formRequest.getId());
        if (byId == null) {
            return Result.fail("属性分组ID不存在");
        }
        Category category = categoryService.getById(formRequest.getCategoryId());
        // 检查分类ID
        if (category == null) {
            return Result.fail("分类ID不存在");
        }
        if (category.getLevel() != 3) {
            return Result.fail("仅支持三级分类ID");
        }
        return Result.byFlag(attributeGroupService.update(formRequest));
    }

    /**
     * 删除属性分组
     */
    @PostMapping("group/delete")
    public Result<String> groupDelete(@RequestBody @Validated(value = DeleteValidGroup.class) AttributeGroupFormRequest formRequest) {
        AttributeGroup byId = attributeGroupService.getById(formRequest.getId());
        if (byId == null) {
            return Result.fail("属性分组ID不存在");
        }
        return Result.byFlag(attributeGroupService.delete(formRequest.getId()));
    }
}
