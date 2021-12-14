package com.mochi.shorturl.model.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "t_short_url_info")
@Entity
@Data
public class TShortUrlInfoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String longUrl;

    private String shortUrl;

    private LocalDateTime createTime;

    private LocalDateTime disableTime;

    private String createIp;

}
