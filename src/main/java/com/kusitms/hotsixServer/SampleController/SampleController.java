package com.kusitms.hotsixServer.SampleController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SampleController {

    @GetMapping(value = "/sample")
    public String sample(){
        log.info("test");
        return "hello springboot!" ;
    }
}