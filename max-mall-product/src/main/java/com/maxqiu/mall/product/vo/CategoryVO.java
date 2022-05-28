package com.maxqiu.mall.product.vo;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.maxqiu.mall.product.entity.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 商品分类 VO
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
    private Integer level;

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
     * 商品数量
     */
    private Integer productCount;

    /**
     * 子分类
     */
    private List<CategoryVO> childList;

    public CategoryVO(Category category, List<CategoryVO> childList) {
        BeanUtils.copyProperties(category, this);
        if (!CollectionUtils.isEmpty(childList)) {
            this.childList = childList;
        }
    }
}
