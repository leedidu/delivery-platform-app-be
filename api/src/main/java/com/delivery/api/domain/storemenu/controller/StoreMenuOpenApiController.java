package com.delivery.api.domain.storemenu.controller;

import com.delivery.api.common.api.Api;
import com.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-menu")
public class StoreMenuOpenApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    @PostMapping("/register")
    public Api<StoreMenuResponse> register(
            @Valid
            @RequestBody Api<StoreMenuRegisterRequest> request
    ){
        var req = request.getBody();
        var response = storeMenuBusiness.register(req);
        return Api.OK(response);
    }
}
