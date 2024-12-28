package com.blog.utils;

import com.blog.entity.vo.PageCalculate;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {
    public void setPage(PageCalculate pageReq) {
        pageReq.setPage((pageReq.getPage() - 1) * pageReq.getLimit());
    }
}
