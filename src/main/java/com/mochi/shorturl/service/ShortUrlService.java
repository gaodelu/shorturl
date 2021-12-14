package com.mochi.shorturl.service;

import com.mochi.shorturl.model.request.ShortUrlAddRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;

public interface ShortUrlService {

    String addShortUrl(ShortUrlAddRequest shortUrlAddRequest, ServerHttpRequest httpRequest);

    boolean delShortUrl(ShortUrlAddRequest shortUrlAddRequest);
}
