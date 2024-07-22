package com.codechallege.rivo.service;

import com.codechallege.rivo.models.request.RequestLogin;
import com.codechallege.rivo.models.request.RequestRegister;
import com.codechallege.rivo.models.response.ResponseGetMe;
import com.codechallege.rivo.models.response.ResponseToken;

public interface AppService {
    String ping();

    String register(RequestRegister requestRegister);

    ResponseToken login(RequestLogin requestLogin);

    ResponseGetMe getMe();
}
