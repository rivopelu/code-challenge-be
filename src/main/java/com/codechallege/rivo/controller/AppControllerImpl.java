package com.codechallege.rivo.controller;

import com.codechallege.rivo.advices.BaseControllerImpl;
import com.codechallege.rivo.models.request.RequestLogin;
import com.codechallege.rivo.models.request.RequestRegister;
import com.codechallege.rivo.models.response.BaseResponse;
import com.codechallege.rivo.models.response.ResponseHelper;
import com.codechallege.rivo.service.AppService;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class AppControllerImpl implements AppController {

    private final AppService appService;

    @Override
    public BaseResponse ping() {
        return ResponseHelper.createBaseResponse(appService.ping());
    }

    @Override
    public BaseResponse register(RequestRegister requestRegister) {
        return ResponseHelper.createBaseResponse(appService.register(requestRegister));
    }

    @Override
    public BaseResponse login(RequestLogin requestLogin) {
        return ResponseHelper.createBaseResponse(appService.login(requestLogin));
    }

    @Override
    public BaseResponse getMe() {
        return ResponseHelper.createBaseResponse(appService.getMe());
    }
}
