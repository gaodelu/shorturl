package com.mochi.shorturl.model;

import com.mochi.shorturl.exception.BizException;
import lombok.Data;

@Data
public class PublicReponse<T> {

    private String code;

    private String msg;

    private T data;

    public PublicReponse(T data) {
        this.code = "0000";
        this.msg = "成功";
        this.data = data;
    }

    public PublicReponse(BizException exception) {
        this.code = exception.getErrCode();
        this.msg = exception.getErrMsg();
    }
}
