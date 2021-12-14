package com.mochi.shorturl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RedirectController {

    @GetMapping("/{shortUrl}")
    public void redirectSending(@PathVariable("shortUrl") String shortUrl){
        log.info("接收到重定向请求:{}",shortUrl);

    }
}
