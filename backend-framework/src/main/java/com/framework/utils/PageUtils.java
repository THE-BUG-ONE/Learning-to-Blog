package com.framework.utils;

import com.framework.entity.vo.request.PageCalculate;

public class PageUtils {
    public static void setCurrent(PageCalculate pageReq) {
        pageReq.setCurrent((pageReq.getCurrent() - 1) * pageReq.getSize());
    }
}
