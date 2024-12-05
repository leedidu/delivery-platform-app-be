package com.delivery.api.domain.user.model;

import com.delivery.db.user.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserStatus status;
    private String address;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime lastLoginAt;
}
