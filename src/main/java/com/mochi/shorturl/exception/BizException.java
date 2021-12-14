package com.mochi.shorturl.exception;

import com.mochi.shorturl.util.ErrCodeConstants;
import lombok.Data;

@Data
public class BizException extends RuntimeException{

    private String errCode;

    private String errMsg;

    public BizException(ErrCodeConstants errCodeConstants){
        this.errCode = errCodeConstants.getErrCode();
        this.errMsg = errCodeConstants.getErrMsg();
    }

}
