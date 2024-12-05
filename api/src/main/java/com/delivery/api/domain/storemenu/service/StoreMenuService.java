package com.delivery.api.domain.storemenu.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.storeMenu.StoreMenuEntity;
import com.delivery.db.storeMenu.StoreMenuRepository;
import com.delivery.db.storeMenu.enums.StoreMenuStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    // 메뉴 등록
    public StoreMenuEntity register(
            StoreMenuEntity storeMenuEntity
    ){
        return Optional.ofNullable(storeMenuEntity)
                .map(it -> {
                    it.setStatus(StoreMenuStatus.REGISTERED);
                    return storeMenuRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 기본적으로 하나의 메뉴를 가져오는 메서드
    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        var entity = storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatus.REGISTERED);

        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // storeId를 가지고 스토어 전체 메뉴를 가져오는 메서드
    public List<StoreMenuEntity> getStoreMenuById(Long id){
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(id, StoreMenuStatus.REGISTERED);
    }
}
