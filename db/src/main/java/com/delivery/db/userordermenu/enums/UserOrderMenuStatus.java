package com.delivery.db.userordermenu.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserOrderMenuStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지")
    ;

//    UserOrderStatus(String description) { // @AllArgsConstructor 쓰는 것과 동
//        this.description = description;
//    }

    private String description;
}
