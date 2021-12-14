package com.mochi.shorturl.controller;

import com.mochi.shorturl.exception.BizException;
import com.mochi.shorturl.model.PublicReponse;
import com.mochi.shorturl.model.request.ShortUrlAddRequest;
import com.mochi.shorturl.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/shorturl/")
@Slf4j
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @PostMapping("add")
    public Mono<PublicReponse<String>> addShortUrl(@RequestBody ShortUrlAddRequest shortUrlAddRequest, ServerHttpRequest httpRequest) {
        log.info("短链接新增请求参数:{}", shortUrlAddRequest);
        try {
            shortUrlAddRequest.check();
        } catch (BizException e) {
            return Mono.justOrEmpty(new PublicReponse<>(e));
        }
        return Mono.justOrEmpty(new PublicReponse<>(shortUrlService.addShortUrl(shortUrlAddRequest,httpRequest)));
    }

    @PostMapping("delete")
    public Mono<PublicReponse<Boolean>> delShortUrl(@RequestBody ShortUrlAddRequest shortUrlAddRequest, ServerHttpRequest httpRequest) {
        log.info("短链接删除请求参数:{}", shortUrlAddRequest);
        try {
            shortUrlAddRequest.check();
        } catch (BizException e) {
            return Mono.justOrEmpty(new PublicReponse<>(e));
        }
        return Mono.justOrEmpty(new PublicReponse<>(shortUrlService.delShortUrl(shortUrlAddRequest)));
    }
}
