package com.kusitms.hotsixServer.SampleController;

import com.kusitms.hotsixServer.global.config.S3UploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SampleController {

    private final S3UploadUtil s3UploadUtil;

    @GetMapping(value = "/sample")
    public String sample(){
        log.info("ci/cd 시간 체크");
        return "hello springboot!" ;
    }

    @PostMapping(value = "/stickers")
    public String  stickers(@RequestPart(value = "file", required = false) MultipartFile multipartFile){
        return s3UploadUtil.upload(multipartFile, "sticker");
    }
}