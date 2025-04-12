package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.SiteConfig;

/**
* @author Felz
* @description 针对表【site_config】的数据库操作Service
* @createDate 2025-04-11 21:31:39
*/
public interface SiteConfigService extends IService<SiteConfig> {
    SiteConfig getSiteConfig();
}
