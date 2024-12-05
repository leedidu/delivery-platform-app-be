package com.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreStatus {
    // 대기 상태
    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    private String description;
}
