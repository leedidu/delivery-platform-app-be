package com.delivery.api.domain.storemenu.convert;

import com.delivery.api.common.annotation.Converter;
import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import com.delivery.db.storeMenu.StoreMenuEntity;

import java.util.List;
import java.util.Optional;

@Converter
public class StoreMenuConverter {

    // toEntity
    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it -> {
                    return StoreMenuEntity.builder()
                            .storeId(request.getStoreId())
                            .name(request.getName())
                            .amount(request.getAmount())
                            .thumbnailUrl(request.getThumbnailUrl())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // toResponse
    public StoreMenuResponse toResponse(StoreMenuEntity entity){
        return Optional.ofNullable(entity)
                .map(it -> {
                    return StoreMenuResponse.builder()
                            .id(entity.getId())
                            .storeId(entity.getStoreId())
                            .name(entity.getName())
                            .amount(entity.getAmount())
                            .status(entity.getStatus())
                            .thumbnailUrl(entity.getThumbnailUrl())
                            .likeCount(entity.getLikeCount())
                            .sequence(entity.getSequence())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // entity list -> response list로 변환해주는 toResponse
    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list){
        return list.stream()
                .map(this::toResponse)
                .toList();
    }
}
