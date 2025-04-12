package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.SiteConfig;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Felz
* @description 针对表【site_config】的数据库操作Mapper
* @createDate 2025-04-11 21:31:39
* @Entity com.blog.entity.dao.SiteConfig
*/
@Mapper
public interface SiteConfigMapper extends BaseMapper<SiteConfig> {

}




