package com.delivery.db.store;

import com.delivery.db.store.enums.StoreCategory;
import com.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    // id + status
    // select * from store where id = ? and status = ? order by id desc limit 1
    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);

    // 유효한 스토어 리스트
    // select * from store where status = ? order id desc
    List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

    // 유효한 특정 카테고리의 스토어 리스트(별점 순으로)
    // select * from store where status = ? and category = ? order star desc
    List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory category);
}