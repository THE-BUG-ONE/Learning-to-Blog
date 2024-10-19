package com.blog.filter;

import com.blog.entity.vo.Result;
import com.blog.constants.SystemConstants;
import com.blog.utils.WebUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@Order(SystemConstants.ORDER_LIMIT)
public class FlowLimitFilter extends HttpFilter {

    @Resource
    private StringRedisTemplate template;

    @Resource
    private WebUtils webUtils;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String address = request.getRemoteAddr();
        if (tryCount(address))
            chain.doFilter(request, response);
        else
            webUtils.renderString(response, Result.failure());
    }

    //尝试IP计数
    private boolean tryCount(String ip) {
        synchronized (ip.intern()) {    //IP计数加锁防止同时有多个请求
            return !Boolean.TRUE.equals(template.hasKey(SystemConstants.FLOW_LIMIT_BLOCK + ip)) &&
                    limitFrequencyCheck(ip);
        }
    }

    //IP计数检查
    private boolean limitFrequencyCheck(String ip) {
        String key = SystemConstants.FLOW_LIMIT_COUNTER + ip;
        if (Boolean.TRUE.equals(template.hasKey(key)) &&
                Optional.ofNullable(template.opsForValue().increment(key)).orElse(1L) >
                        10) {   //3s内计数超过10次封禁IP
                template.opsForValue()
                        .set(SystemConstants.FLOW_LIMIT_BLOCK + ip, "", 30, TimeUnit.SECONDS);
        }
        else
            template.opsForValue().set(key, "1", 3, TimeUnit.SECONDS);
        return true;
    }
}
