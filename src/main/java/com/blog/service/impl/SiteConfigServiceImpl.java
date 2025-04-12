package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.SiteConfig;
import com.blog.mapper.SiteConfigMapper;
import com.blog.service.SiteConfigService;
import org.springframework.stereotype.Service;

/**
* @author Felz
* @description 针对表【site_config】的数据库操作Service实现
* @createDate 2025-04-11 21:31:39
*/
@Service
public class SiteConfigServiceImpl extends ServiceImpl<SiteConfigMapper, SiteConfig> implements SiteConfigService{

    @Override
    public SiteConfig getSiteConfig() {
        return getOne(null);
    }
}




