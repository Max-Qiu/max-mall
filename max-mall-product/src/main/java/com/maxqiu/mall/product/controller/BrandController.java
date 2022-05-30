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
import com.maxqiu.mall.product.entity.Brand;
import com.maxqiu.mall.product.rquest.BrandRequest;
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
     * 获取品牌列表
     */
    @GetMapping("list")
    public Result<List<BrandVO>> list() {
        return Result.success(brandService.listVO());
    }

    // TODO 可能要做分页搜索接口

    /**
     * 新增品牌
     */
    @PostMapping("save")
    public Result<String> save(@RequestBody @Validated(value = AddValidGroup.class) BrandRequest request) {
        return Result.byFlag(brandService.save(request));
    }

    /**
     * 修改品牌
     */
    @PostMapping("update")
    public Result<String> update(@RequestBody @Validated(value = UpdateValidGroup.class) BrandRequest request) {
        Brand byId = brandService.getById(request.getId());
        if (byId == null) {
            return Result.fail("品牌ID不存在");
        }
        return Result.byFlag(brandService.update(request));
    }

    /**
     * 删除分类
     */
    @PostMapping("remove")
    public Result<String> remove(@RequestBody @Validated(value = DeleteValidGroup.class) BrandRequest request) {
        Brand byId = brandService.getById(request.getId());
        if (byId == null) {
            return Result.fail("品牌ID不存在");
        }
        // TODO 校验商品是否关联
        return Result.byFlag(brandService.removeById(request.getId()));
    }
}
