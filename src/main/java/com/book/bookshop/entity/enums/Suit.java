package com.book.bookshop.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Data;

/**
 * @Description: 套装枚举类
 */
public enum Suit {
    YES(1,"是"),NO(2,"否");

    Suit(int code, String desc) {
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
