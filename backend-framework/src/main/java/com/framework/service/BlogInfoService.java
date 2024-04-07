package com.framework.service;

import com.framework.Result;
import com.framework.constants.SystemConstants;
import com.framework.entity.dao.Article;
import com.framework.entity.vo.response.BlogBackInfoResp;
import com.framework.entity.vo.response.BlogInfoResp;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class BlogInfoService {

    @Lazy
    @Resource
    private SiteConfigService siteConfigService;

    @Lazy
    @Resource
    private ArticleService articleService;

    @Lazy
    @Resource
    private TagService tagService;

    @Lazy
    @Resource
    private CategoryService categoryService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Result<BlogInfoResp> getBlogInfo() {
        BlogInfoResp blogInfoResp = new BlogInfoResp(
                articleService.lambdaQuery()
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                        .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE).count(),
                categoryService.count(null),
                siteConfigService.getSiteConfig(),
                tagService.count(null),
                Optional.ofNullable(stringRedisTemplate.opsForValue()
                        .get(SystemConstants.BLOG_VIEW_COUNT)).orElse(String.valueOf(0)));
        return Result.success(blogInfoResp);
    }

    public Result<String> about() {
        String about = siteConfigService.query().one().getAboutMe();
        return about != null ?
                Result.success(about) :
                Result.failure();
    }

    public Result<?> report() {
        stringRedisTemplate.opsForValue().increment(SystemConstants.BLOG_VIEW_COUNT, 1);
        return Result.success();
    }

    public BlogBackInfoResp getBlogBackInfo() {
        return null;
    }
}
