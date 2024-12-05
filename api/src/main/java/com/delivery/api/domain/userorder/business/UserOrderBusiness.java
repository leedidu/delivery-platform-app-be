package com.delivery.api.domain.userorder.business;

import com.delivery.api.common.annotation.Business;
import com.delivery.api.domain.store.converter.StoreConverter;
import com.delivery.api.domain.store.service.StoreService;
import com.delivery.api.domain.storemenu.convert.StoreMenuConverter;
import com.delivery.api.domain.storemenu.service.StoreMenuService;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import com.delivery.api.domain.userorder.converter.UserOrderConverter;
import com.delivery.api.domain.userorder.service.UserOrderService;
import com.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import com.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    /*
    사용자 주문 로직
      1. 사용자, 메뉴, id 리스트 받아오기
      2. UserOrder 생성
      3. UserOrderMenu 생성
      4. 응답생성
    * */

    public UserOrderResponse userOrder(User user, @Valid UserOrderRequest body) {
        // 받아온 메뉴가 유효한지 체크 >> 유효하면 메뉴정보 가져오기
        var storeMenuEntityList = body.getStoreMenuIdList().stream().map(storeMenuService::getStoreMenuWithThrow)
                .toList();

        var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

        // 주문 : UserOrder 생성 : user_order 테이블에 데이터 저장
        var newUserOrderEntity = userOrderService.order(userOrderEntity);

        // 매핑 : UserOrderMenu 생성 : user_order_menu 테이블에 데이터 저장
        var userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> {
                    // user order
                    var userOrderMenuEntity = userOrderMenuConverter.toEntity(newUserOrderEntity, it);
                    return userOrderMenuEntity;
                })
                .toList();

        // 주문 내역 기록 남기기
        userOrderMenuEntityList.forEach(userOrderMenuService::order);

        // to response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {
        // 현재 사용자의 주문 내역 리스트
        var userOrderEntityList = userOrderService.current(user.getId());

        // 주문 1건씩 처리
        var userOrderDetailResponseList = userOrderEntityList.stream().map(userOrderEntity -> {
            // 주문 메뉴가 무엇인지
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity -> {
                var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                return storeMenuEntity;
            })
                    .toList();

            // 주문한 스토어가 어디인지
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).toList();
        return userOrderDetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {
        // 과거 사용자의 주문 내역 리스트
        var userOrderEntityList = userOrderService.history(user.getId());

        // 주문 1건씩 처리
        var userOrderDetailResponseList = userOrderEntityList.stream().map(userOrderEntity -> {
            // 주문 메뉴가 무엇인지
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity -> {
                        var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                        return storeMenuEntity;
                    })
                    .toList();

            // 주문한 스토어가 어디인지
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).toList();
        return userOrderDetailResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {
        // 주문 내역
        var userOrderEntity = userOrderService.getUserOrderWithoutStatusWithThrow(orderId, user.getId());

        // 메뉴
        var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

        var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity -> {
                    var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                })
                .toList();

        // 스토어 정보
        var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }
}
