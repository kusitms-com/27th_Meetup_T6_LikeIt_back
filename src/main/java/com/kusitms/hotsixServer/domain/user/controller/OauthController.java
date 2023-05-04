package com.kusitms.hotsixServer.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kusitms.hotsixServer.domain.user.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/auth")
public class OauthController {

    private final OauthService oauthService;

    @GetMapping(value="/google/callback")
    public void callback (@RequestParam(name="code") String code) throws JsonProcessingException {
        oauthService.oauthLogin(code);
    }
}
