package com.util;

import com.google.gson.Gson;

public class JsonUtil {
    /***
     * 将数据转换成json格式字符串
     * @param data
     * @return
     */

    public static String toJson(Object data)
    {
        Gson gson=new Gson();
        return gson.toJson(data);
    }
}