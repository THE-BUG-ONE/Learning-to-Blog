package com.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.mapper.SiteConfigMapper;
import com.framework.entity.dao.SiteConfig;
import com.framework.service.SiteConfigService;
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

