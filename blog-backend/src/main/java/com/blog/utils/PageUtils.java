package com.blog.utils;

import com.blog.entity.vo.PageCalculate;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {
    public void setCurrent(PageCalculate pageReq) {
        pageReq.setCurrent((pageReq.getCurrent() - 1) * pageReq.getSize());
    }
}
