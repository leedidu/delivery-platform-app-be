package com.delivery.api.domain.userorder.controller.model;

import com.delivery.api.domain.store.controller.model.StoreResponse;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {
    private UserOrderResponse userOrderResponse; // 주문 정보

    private StoreResponse storeResponse; // 스토어 정보

    private List<StoreMenuResponse> storeMenuResponseList; // 메뉴 정보 리스트
}
