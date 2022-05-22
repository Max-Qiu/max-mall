package com.maxqiu.mall.product.vo;

import java.util.List;

import com.maxqiu.mall.product.entity.Category;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 商品分类
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryVO {
    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父分类id
     */
    private Integer parentId;

    /**
     * 层级
     */
    private Integer catLevel;

    /**
     * 是否显示 0不显示 1显示
     */
    private Boolean showStatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标地址
     */
    private String icon;

    /**
     * 计量单位
     */
    private String productUnit;

    /**
     * 商品数量
     */
    private Integer productCount;

    private List<CategoryVO> childList;

    public CategoryVO(Category category, List<CategoryVO> childList) {
        BeanUtil.copyProperties(category, this);
        if (CollectionUtil.isNotEmpty(childList)) {
            this.childList = childList;
        }
    }
}
