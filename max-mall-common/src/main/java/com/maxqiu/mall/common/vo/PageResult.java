package com.maxqiu.mall.common.vo;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 分页返回对象
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
public class PageResult<T> {
    /**
     * 对象集合
     */
    private List<T> list;

    /**
     * 页码
     */
    private Long pageNumber;

    /**
     * 页面大小
     */
    private Long pageSize;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总行数
     */
    private Long totalRow;

    public PageResult(List<T> collect, Page<?> page) {
        this.list = collect;
        this.pageNumber = page.getCurrent();
        this.pageSize = page.getSize();
        this.totalPage = page.getPages();
        this.totalRow = page.getTotal();
    }
}
