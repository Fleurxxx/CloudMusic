package com.util;

import com.entity.Result;

public class ResultUtil {
    /**
     *成功
     */
    public static String ac(String message, Object data){
        return JsonUtil.toJson(new Result(true,200,message,data));
    }


    /**
     *失败
     */
    public static String wa(String message, Object data){
        return JsonUtil.toJson(new Result(false,400,message,data));
    }
}
