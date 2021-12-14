package com.mochi.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 短链接服务入口
 */
@SpringBootApplication
public class ShorturlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShorturlApplication.class, args);
    }

}
