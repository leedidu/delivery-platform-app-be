package com.delivery.db.store;

import com.delivery.db.BaseEntity;
import com.delivery.db.store.enums.StoreCategory;
import com.delivery.db.store.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Entity
@Table(name = "store")
public class StoreEntity extends BaseEntity {
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING) // enum 타입의 스트링이 저장
    private StoreStatus status;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    @Column(name = "star")
    private Double star;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal minimumAmount;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal minimumDeliveryAmount;

    @Column(length = 20)
    private String phoneNumber;
}
