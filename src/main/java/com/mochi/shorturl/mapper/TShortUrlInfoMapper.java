package com.mochi.shorturl.mapper;

import com.mochi.shorturl.model.model.TShortUrlInfoModel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface TShortUrlInfoMapper extends PagingAndSortingRepository<TShortUrlInfoModel, Integer> {

    TShortUrlInfoModel findByLongUrlAndDisableTimeAfter(String longUrl, LocalDateTime disableTime);

    @Query("update TShortUrlInfoModel set disableTime=:disableTime where id = :id")
    @Transactional
    @Modifying
    void updateDisableTime(@Param("disableTime")LocalDateTime disableTime, @Param("id")Integer id);
}
