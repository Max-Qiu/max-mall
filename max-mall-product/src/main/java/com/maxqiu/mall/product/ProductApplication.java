package com.maxqiu.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 商品 服务
 *
 * @author Max_Qiu
 */
@SpringBootApplication(scanBasePackages = {"com.maxqiu.mall.product", "com.maxqiu.mall.common"})
@MapperScan("com.maxqiu.mall.product.mapper")
@EnableCaching
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
