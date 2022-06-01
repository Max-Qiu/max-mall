package com.maxqiu.mall.common.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用分页请求
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BasePageRequest {
    /**
     * 当前页码
     */
    private Integer pageNumber = 1;

    /**
     * 页面大小
     */
    private Integer pageSize = 10;
}
