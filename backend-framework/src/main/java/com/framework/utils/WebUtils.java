package com.framework.utils;

import com.framework.Result;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class WebUtils {

    public static <T> void  renderString(HttpServletResponse response, Result<T> result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(result.code());
        response.getWriter().write(result.asJsonString());
    }
}
