package com.mochi.shorturl.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrCodeConstants {

   PARAM_CAN_NOT_BE_NULL("0001","参数不能为空"),
   LONG_URL_IS_NOT_EXISTS("0002","删除失败");

   private String errCode;

   private String errMsg;
}
