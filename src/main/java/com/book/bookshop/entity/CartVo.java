package com.book.bookshop.entity;

import lombok.Data;

/**
 * @Description:购物车查询展示包装类
 */
@Data
public class CartVo {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private Integer count;
    private String bookName;
    private String imgUrl;
    private double newPrice;
}
