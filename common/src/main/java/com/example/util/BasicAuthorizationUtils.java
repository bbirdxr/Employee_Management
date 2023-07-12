package com.example.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

public class BasicAuthorizationUtils {
    private static final Base64.Decoder decoder = Base64.getDecoder();


    public static boolean isAuth(HttpServletRequest req) {
        String base6AuthStr = req.getHeader("Authorization");
        if (base6AuthStr == null) {
            return false;
        }

        String authStr = new String(decoder.decode(base6AuthStr.substring(6).getBytes()));
        String[] arr = authStr.split(":");
        if (arr.length == 2) {
            String username = arr[0];
            String password = arr[1];
            // 校验用户名和密码
            return "user".equals(username) && "password".equals(password);
        }
        return false;
    }
}
