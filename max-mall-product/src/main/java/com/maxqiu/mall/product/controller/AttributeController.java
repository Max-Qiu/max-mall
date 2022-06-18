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
import com.maxqiu.mall.product.entity.Attribute;
import com.maxqiu.mall.product.entity.AttributeGroup;
import com.maxqiu.mall.product.entity.Category;
import com.maxqiu.mall.product.enums.AttributeTypeEnum;
import com.maxqiu.mall.product.rquest.AttributeBaseFormRequest;
import com.maxqiu.mall.product.rquest.AttributeBasePageRequest;
import com.maxqiu.mall.product.rquest.AttributeGroupFormRequest;
import com.maxqiu.mall.product.rquest.AttributeGroupPageRequest;
import com.maxqiu.mall.product.rquest.AttributeSaleFormRequest;
import com.maxqiu.mall.product.rquest.AttributeSalePageRequest;
import com.maxqiu.mall.product.service.AttributeGroupService;
import com.maxqiu.mall.product.service.AttributeService;
import com.maxqiu.mall.product.service.CategoryService;
import com.maxqiu.mall.product.vo.AttributeBaseVO;
import com.maxqiu.mall.product.vo.AttributeGroupVO;
import com.maxqiu.mall.product.vo.AttributeSaleVO;

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
    private AttributeService attributeService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据分类ID分页获取属性分组列表
     */
    @GetMapping("group/page")
    public Result<PageResult<AttributeGroupVO>> groupPage(AttributeGroupPageRequest pageRequest) {
        Page<AttributeGroup> page =
            attributeGroupService.pageByCategoryId(pageRequest.getPageNumber(), pageRequest.getPageSize(), pageRequest.getCategoryId());
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

    /**
     * 根据分类ID分页获取基本属性列表
     */
    @GetMapping("base/page")
    public Result<PageResult<AttributeBaseVO>> basePage(AttributeBasePageRequest pageRequest) {
        Page<Attribute> page = attributeService.pageByCategoryIdAndType(AttributeTypeEnum.BASE, pageRequest.getPageNumber(),
            pageRequest.getPageSize(), pageRequest.getCategoryId());
        List<AttributeBaseVO> collect =
            page.getRecords().stream().map(e -> new AttributeBaseVO(e, categoryService.getById(e.getCategoryId()).getName(),
                attributeGroupService.getById(e.getGroupId()).getName())).collect(Collectors.toList());
        return Result.success(new PageResult<>(collect, page));
    }

    /**
     * 新增基本属性
     */
    @PostMapping("base/create")
    public Result<String> baseCreate(@RequestBody @Validated(value = CreateValidGroup.class) AttributeBaseFormRequest formRequest) {
        Category category = categoryService.getById(formRequest.getCategoryId());
        // 检查分类ID
        if (category == null) {
            return Result.fail("分类ID不存在");
        }
        if (category.getLevel() != 3) {
            return Result.fail("仅支持三级分类ID");
        }
        AttributeGroup group = attributeGroupService.getById(formRequest.getGroupId());
        if (group == null) {
            return Result.fail("属性分组ID不存在");
        }
        return Result.byFlag(attributeService.create(formRequest));
    }

    /**
     * 修改基本属性
     */
    @PostMapping("base/update")
    public Result<String> baseUpdate(@RequestBody @Validated(value = UpdateValidGroup.class) AttributeBaseFormRequest formRequest) {
        Attribute byId = attributeService.getById(formRequest.getId());
        if (byId == null) {
            return Result.fail("基本属性ID不存在");
        }
        Category category = categoryService.getById(formRequest.getCategoryId());
        // 检查分类ID
        if (category == null) {
            return Result.fail("分类ID不存在");
        }
        if (category.getLevel() != 3) {
            return Result.fail("仅支持三级分类ID");
        }
        AttributeGroup group = attributeGroupService.getById(formRequest.getGroupId());
        if (group == null) {
            return Result.fail("属性分组ID不存在");
        }
        return Result.byFlag(attributeService.update(formRequest));
    }

    /**
     * 删除基本属性
     */
    @PostMapping("base/delete")
    public Result<String> baseDelete(@RequestBody @Validated(value = DeleteValidGroup.class) AttributeBaseFormRequest formRequest) {
        Attribute byId = attributeService.getById(formRequest.getId());
        if (byId == null) {
            return Result.fail("基本属性ID不存在");
        }
        return Result.byFlag(attributeService.delete(formRequest.getId()));
    }

    /**
     * 根据分类ID分页获取销售属性列表
     */
    @GetMapping("sale/page")
    public Result<PageResult<AttributeSaleVO>> salePage(AttributeSalePageRequest pageRequest) {
        Page<Attribute> page = attributeService.pageByCategoryIdAndType(AttributeTypeEnum.SALE, pageRequest.getPageNumber(),
            pageRequest.getPageSize(), pageRequest.getCategoryId());
        List<AttributeSaleVO> collect = page.getRecords().stream()
            .map(e -> new AttributeSaleVO(e, categoryService.getById(e.getCategoryId()).getName())).collect(Collectors.toList());
        return Result.success(new PageResult<>(collect, page));
    }

    /**
     * 新增销售属性
     */
    @PostMapping("sale/create")
    public Result<String> saleCreate(@RequestBody @Validated(value = CreateValidGroup.class) AttributeSaleFormRequest formRequest) {
        Category category = categoryService.getById(formRequest.getCategoryId());
        // 检查分类ID
        if (category == null) {
            return Result.fail("分类ID不存在");
        }
        if (category.getLevel() != 3) {
            return Result.fail("仅支持三级分类ID");
        }
        return Result.byFlag(attributeService.create(formRequest));
    }

    /**
     * 修改销售属性
     */
    @PostMapping("sale/update")
    public Result<String> saleUpdate(@RequestBody @Validated(value = UpdateValidGroup.class) AttributeSaleFormRequest formRequest) {
        Attribute byId = attributeService.getById(formRequest.getId());
        if (byId == null) {
            return Result.fail("销售属性ID不存在");
        }
        Category category = categoryService.getById(formRequest.getCategoryId());
        // 检查分类ID
        if (category == null) {
            return Result.fail("分类ID不存在");
        }
        if (category.getLevel() != 3) {
            return Result.fail("仅支持三级分类ID");
        }
        return Result.byFlag(attributeService.update(formRequest));
    }

    /**
     * 删除销售属性
     */
    @PostMapping("sale/delete")
    public Result<String> saleDelete(@RequestBody @Validated(value = DeleteValidGroup.class) AttributeSaleFormRequest formRequest) {
        Attribute byId = attributeService.getById(formRequest.getId());
        if (byId == null) {
            return Result.fail("销售属性ID不存在");
        }
        return Result.byFlag(attributeService.delete(formRequest.getId()));
    }
}
