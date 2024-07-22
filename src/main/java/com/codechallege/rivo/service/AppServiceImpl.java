package com.codechallege.rivo.service;

import com.codechallege.rivo.entities.Account;
import com.codechallege.rivo.entities.AccountRepository;
import com.codechallege.rivo.exception.BadRequestException;
import com.codechallege.rivo.exception.NotAuthorizedException;
import com.codechallege.rivo.exception.SystemErrorException;
import com.codechallege.rivo.models.request.RequestLogin;
import com.codechallege.rivo.models.request.RequestRegister;
import com.codechallege.rivo.models.response.ResponseGetMe;
import com.codechallege.rivo.models.response.ResponseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final HttpServletRequest httpServletRequest;


    @Override
    public String ping() {
        return "PONG";
    }

    @Override
    public String register(RequestRegister requestRegister) {
        String password = passwordEncoder.encode(requestRegister.getPassword());
        boolean checkUsername = accountRepository.existsByUsername(requestRegister.getUsername());
        if (checkUsername) {
            throw new BadRequestException("Username already exists");
        }
        try {
            Account account = Account.builder()
                    .name(requestRegister.getName())
                    .password(password)
                    .username(requestRegister.getUsername())
                    .avatar(createAvatar(requestRegister.getName()))
                    .build();
            accountRepository.save(account);
            return "Success";

        } catch (Exception e) {
            throw new SystemErrorException(e.getMessage());
        }


    }

    @Override
    public ResponseToken login(RequestLogin requestLogin) {
        Optional<Account> findAccount = accountRepository.findByUsername(requestLogin.getUsername());
        if (findAccount.isEmpty()) {
            throw new BadRequestException("Login Failed");
        }
        try {
            Account account = findAccount.get();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), requestLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtService.generateToken(userDetails);
            return ResponseToken.builder().accessToken(jwt).build();
        } catch (Exception e) {
            throw new SystemErrorException(e.getMessage());
        }
    }

    @Override
    public ResponseGetMe getMe() {
        Account account = getCurrentAccount();
        if (account == null) {
            throw new NotAuthorizedException("Not Authorized");
        }
        return ResponseGetMe.builder()
                .name(account.getName())
                .username(account.getUsername())
                .avatar(account.getAvatar())
                .build();
    }

    private Account getCurrentAccount() {
        String currentUserId = httpServletRequest.getAttribute("x_who").toString();
        Optional<Account> account = accountRepository.findById(currentUserId);
        return account.orElse(null);
    }


    private String createAvatar(String name) {
        return "https://ui-avatars.com/api/?background=random&name=" + name;
    }
}
