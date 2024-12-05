package com.delivery.api.domain.user.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.error.UserErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.user.UserEntity;
import com.delivery.db.user.UserRepository;
import com.delivery.db.user.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserEntity register(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity is null"));
    }

    // 로그인 검증 -> 검증하면서 user entity 반환
    public UserEntity getUserWithThrow(String email, String password){
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(email, password, UserStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(
            Long userId
    ){
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
                userId,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }


    public UserEntity login(@NotBlank String email, @NotBlank String password) {
        var entity = getUserWithThrow(email, password);
        return entity;
    }
}
