package com.framework.utils;

import com.framework.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebUtils {

    public <T> void  renderString(HttpServletResponse response, Result<T> result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(result.code());
        response.getWriter().write(result.asJsonString());
    }
}
