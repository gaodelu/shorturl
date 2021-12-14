package com.mochi.shorturl.model.request;

import com.mochi.shorturl.exception.BizException;
import com.mochi.shorturl.util.ErrCodeConstants;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ShortUrlAddRequest {

    private String longUrl;

    public void check() {
        if (StringUtils.isEmpty(this.longUrl)){
            throw new BizException(ErrCodeConstants.PARAM_CAN_NOT_BE_NULL);
        }
    }

}
