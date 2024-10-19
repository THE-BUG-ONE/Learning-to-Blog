package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.SiteConfig;
import com.blog.mapper.SiteConfigMapper;
import com.blog.service.SiteConfigService;
import org.springframework.stereotype.Service;

/**
 * (SiteConfig)表服务实现类
 *
 * @author makejava
 * @since 2024-04-02 15:00:55
 */
@Service("siteConfigService")
public class SiteConfigServiceImpl extends ServiceImpl<SiteConfigMapper, SiteConfig> implements SiteConfigService {

    @Override
    public SiteConfig getSiteConfig() {
        return this.query().one();
    }
}

