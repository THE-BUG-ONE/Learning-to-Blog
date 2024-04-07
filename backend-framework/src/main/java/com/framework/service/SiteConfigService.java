package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.SiteConfig;

/**
 * (SiteConfig)表服务接口
 *
 * @author makejava
 * @since 2024-04-02 15:00:55
 */
public interface SiteConfigService extends IService<SiteConfig> {
    SiteConfig getSiteConfig();
}

