package com.maxqiu.mall.product.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 品牌分类关联
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@TableName("mm_brand_category_relation")
public class BrandCategoryRelation extends Model<BrandCategoryRelation> {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableField("brand_id")
    private Integer brandId;

    /**
     * 分类id
     */
    @TableField("category_id")
    private Integer categoryId;

    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;

    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;

    @Override
    public Serializable pkVal() {
        return this.categoryId;
    }
}
