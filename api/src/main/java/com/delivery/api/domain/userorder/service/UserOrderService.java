package com.delivery.api.domain.userorder.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.userorder.UserOrderEntity;
import com.delivery.db.userorder.UserOrderRepository;
import com.delivery.db.userorder.enums.UserOrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    // 특정 주문의 특정 주문
    public UserOrderEntity getUserOrderWithoutStatusWithThrow(Long id, Long userId) {
        return userOrderRepository.findAllByIdAndUserId(id, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId) {
        return userOrderRepository.findAllByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 특정 유저의 모든 주문
    public List<UserOrderEntity> getUserOrderList(Long userId) {
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
    }

    // status를 외부로부터 받도록 수정(특정 유저의 모든 주문 중 status 리스트에 있는 status를 가진)
    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList) {
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
    }

    // 현재 진행중인 내역
    public List<UserOrderEntity> current(Long userId){
        return getUserOrderList(
                userId,
                List.of(
                        UserOrderStatus.ORDER,
                        UserOrderStatus.ACCEPT,
                        UserOrderStatus.COOKING,
                        UserOrderStatus.DELIVERY));
    }

    //과거 주문한 내역
    public List<UserOrderEntity> history(Long userId){
        return getUserOrderList(
                userId,
                List.of(
                        UserOrderStatus.RECEIVE));
    }

    // 주문
    public UserOrderEntity order(UserOrderEntity userOrderEntity) {
        return Optional.ofNullable(userOrderEntity)
                .map(it -> {
                    it.setStatus(UserOrderStatus.ORDER);
                    it.setOrderedAt(LocalDateTime.now());
                    return userOrderRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 상태 변경
    public UserOrderEntity setStatus(UserOrderEntity userOrderEntity, UserOrderStatus status) {
        userOrderEntity.setStatus(status);
        return userOrderRepository.save(userOrderEntity);
    }

    // 주문확인
    public UserOrderEntity accept(UserOrderEntity userOrderEntity) {
        userOrderEntity.setAcceptedAt(LocalDateTime.now());
        return setStatus(userOrderEntity, UserOrderStatus.ACCEPT);
    }

    // 조리시작
    public UserOrderEntity cooking(UserOrderEntity userOrderEntity) {
        userOrderEntity.setCookingStartedAt(LocalDateTime.now());
        return setStatus(userOrderEntity, UserOrderStatus.COOKING);
    }

    // 배달시작
    public UserOrderEntity delivery(UserOrderEntity userOrderEntity) {
        userOrderEntity.setDeliveryStartedAt(LocalDateTime.now());
        return setStatus(userOrderEntity, UserOrderStatus.DELIVERY);
    }

    // 배달완료
    public UserOrderEntity receive(UserOrderEntity userOrderEntity) {
        userOrderEntity.setReceivedAt(LocalDateTime.now());
        return setStatus(userOrderEntity, UserOrderStatus.RECEIVE);
    }
}
