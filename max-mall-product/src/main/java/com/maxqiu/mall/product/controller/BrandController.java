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
import com.maxqiu.mall.product.entity.Brand;
import com.maxqiu.mall.product.rquest.BrandFormRequest;
import com.maxqiu.mall.product.rquest.BrandPageRequest;
import com.maxqiu.mall.product.service.BrandService;
import com.maxqiu.mall.product.vo.BrandVO;

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

    /**
     * 根据品牌名称分页获取列表
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
        Brand byId = brandService.getById(formRequest.getId());
        if (byId == null) {
            return Result.fail("品牌ID不存在");
        }
        return Result.byFlag(brandService.update(formRequest));
    }

    /**
     * 删除分类
     */
    @PostMapping("delete")
    public Result<String> delete(@RequestBody @Validated(value = DeleteValidGroup.class) BrandFormRequest formRequest) {
        Brand byId = brandService.getById(formRequest.getId());
        if (byId == null) {
            return Result.fail("品牌ID不存在");
        }
        // TODO 校验商品是否关联
        return Result.byFlag(brandService.delete(formRequest.getId()));
    }
}
