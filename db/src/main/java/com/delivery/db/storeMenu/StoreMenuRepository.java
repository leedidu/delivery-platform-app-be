package com.delivery.db.storeMenu;

import com.delivery.db.storeMenu.enums.StoreMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreMenuRepository extends JpaRepository<StoreMenuEntity, Long> {

    // 유효한 단일 메뉴 체크
    // select * from store_menu id = ? and status =? order by id desc limit 1
    Optional<StoreMenuEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreMenuStatus status);

    // 특정 가게 메뉴 가져오기
    // select * from store_menu where store_id = ? and status = ? order by sequence desc;
    List<StoreMenuEntity> findAllByStoreIdAndStatusOrderBySequenceDesc(Long storeId, StoreMenuStatus status);
}
