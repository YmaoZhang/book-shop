package com.book.bookshop.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @Description: 图书类别枚举类
 */
public enum Category {
    SELECTED(1,"精选图书"),RECOMMEND(2,"推荐图书"),BARGAIN(3,"特价图书");

    Category(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @EnumValue//标记数据库存的值是code
    private final int code;
    private final String desc;
}
