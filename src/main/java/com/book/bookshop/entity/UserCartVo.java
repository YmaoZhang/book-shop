package com.book.bookshop.entity;

import lombok.Data;

/**
 * @Description: 用户购物车信息展示对象
 */
@Data
public class UserCartVo {
    //购物车商品总数
    private Integer num;
    //总价格
    private double totalPrice;
}
