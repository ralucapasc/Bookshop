package com.pitech.utils;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeDecodeCookieUtils {
    private static Gson gson = new Gson();

    public static <T> String encode(T object) {
        return Base64.getEncoder().encodeToString(gson.toJson(object).getBytes());
    }

    public static <T> T decode(String object, Class<T> tClass) throws Exception {
        if (object == null || object.isEmpty()) {
            return tClass.newInstance();
        }

        return gson.fromJson(IOUtils.toString(Base64.getDecoder().decode(object), StandardCharsets.UTF_8.name()), tClass);
    }
}


