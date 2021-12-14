package com.mochi.shorturl.service.impl;

import com.mochi.shorturl.exception.BizException;
import com.mochi.shorturl.mapper.TShortUrlInfoMapper;
import com.mochi.shorturl.model.model.TShortUrlInfoModel;
import com.mochi.shorturl.model.request.ShortUrlAddRequest;
import com.mochi.shorturl.service.ShortUrlService;
import com.mochi.shorturl.util.ConvertUtil;
import com.mochi.shorturl.util.ErrCodeConstants;
import com.mochi.shorturl.util.IpAddressUtil;
import com.mochi.shorturl.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final String SHORT_URL_PATTERN = "http://hiyaki.cn/";

    @Autowired
    private TShortUrlInfoMapper tShortUrlInfoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String addShortUrl(ShortUrlAddRequest shortUrlAddRequest, ServerHttpRequest httpRequest) {
        TShortUrlInfoModel tShortUrlInfoModel = tShortUrlInfoMapper.findByLongUrlAndDisableTimeAfter(shortUrlAddRequest.getLongUrl(), LocalDateTime.now());
        log.info("根据长链接查询信息:{}", tShortUrlInfoModel);
        if (tShortUrlInfoModel != null) {
            //存在，则更新失效时间
            tShortUrlInfoMapper.updateDisableTime(LocalDateTime.now().plusDays(1), tShortUrlInfoModel.getId());
            redisUtil.set("SHORT_URL_" + tShortUrlInfoModel.getShortUrl(), shortUrlAddRequest.getLongUrl(), 1, TimeUnit.DAYS);
            return SHORT_URL_PATTERN + tShortUrlInfoModel.getShortUrl();
        }
        //不存在则进行新增操作
        tShortUrlInfoModel = new TShortUrlInfoModel();
        Random random = new Random();
        int id;
        for (; ; ) {
            id = Math.abs(random.nextInt());
            Optional<TShortUrlInfoModel> byId = tShortUrlInfoMapper.findById(id);
            if (!byId.isPresent()) {
                log.info("新增短链接id：{}", id);
                break;
            }
        }
        tShortUrlInfoModel.setLongUrl(shortUrlAddRequest.getLongUrl());
        //进行url生成操作
        tShortUrlInfoModel.setCreateTime(LocalDateTime.now());
        tShortUrlInfoModel.setDisableTime(LocalDateTime.now().plusDays(1));
        tShortUrlInfoModel.setCreateIp(IpAddressUtil.getIpAddress(httpRequest));
        tShortUrlInfoModel.setId(id);
        String encode = ConvertUtil.encode(id);
        tShortUrlInfoModel.setShortUrl(encode);
        log.info("生成短链接ID：{},短链接:{}", id, encode);
        tShortUrlInfoMapper.save(tShortUrlInfoModel);
        //将短链接保存到redis
        log.info("短链接缓存到redis，Key:{}", "SHORT_URL_" + encode);
        redisUtil.set("SHORT_URL_" + encode, shortUrlAddRequest.getLongUrl(), 1, TimeUnit.DAYS);
        return SHORT_URL_PATTERN + encode;
    }

    @Override
    public boolean delShortUrl(ShortUrlAddRequest shortUrlAddRequest) {
        TShortUrlInfoModel tShortUrlInfoModel = tShortUrlInfoMapper.findByLongUrlAndDisableTimeAfter(shortUrlAddRequest.getLongUrl(), LocalDateTime.now());
        if (tShortUrlInfoModel != null) {
            //存在，则更新失效时间
            tShortUrlInfoMapper.updateDisableTime(LocalDateTime.now(), tShortUrlInfoModel.getId());
            redisUtil.set("SHORT_URL_" + tShortUrlInfoModel.getShortUrl(), shortUrlAddRequest.getLongUrl(), 1, TimeUnit.MILLISECONDS);
        } else {
            log.error("长链接{}不存在，无法删除！", shortUrlAddRequest.getLongUrl());
            throw new BizException(ErrCodeConstants.LONG_URL_IS_NOT_EXISTS);
        }
        return true;
    }
}
