package com.delivery.db.userorder.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserOrderStatus {
    REGISTERED("등록"),

    UNREGISTERED("해지"),

    ORDER("주문"),

    ACCEPT("확인"),

    COOKING("요리중"),

    DELIVERY("배달중"),

    RECEIVE("완료")
    ;

//    UserOrderStatus(String description) { // @AllArgsConstructor 쓰는 것과 동
//        this.description = description;
//    }

    private String description;
}
