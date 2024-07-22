package com.codechallege.rivo.controller;

import com.codechallege.rivo.advices.BaseController;
import com.codechallege.rivo.models.request.RequestLogin;
import com.codechallege.rivo.models.request.RequestRegister;
import com.codechallege.rivo.models.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController
public interface AppController {

    @GetMapping("ping")
    BaseResponse ping();

    @PostMapping("/auth/register")
    BaseResponse register(@RequestBody RequestRegister requestRegister);

    @PostMapping("/auth/login")
    BaseResponse login(@RequestBody RequestLogin requestLogin);

    @GetMapping("account/get-me")
    BaseResponse getMe();

}
