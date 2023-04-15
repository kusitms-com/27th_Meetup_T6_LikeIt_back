package com.kusitms.hotsixServer.SampleController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping(value = "/sample")
    public String sample(){
        return "hello springboot!" ;
    }
}