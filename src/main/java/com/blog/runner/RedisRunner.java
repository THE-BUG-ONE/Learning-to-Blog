package com.blog.runner;

import com.blog.constants.SystemConstants;
import com.blog.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisRunner implements CommandLineRunner {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Lazy
    @Resource
    private ArticleService articleService;

    @Override
    public void run(String... args) {
        getArticleLikeAndViewCountToRedis();
    }

    private void getArticleLikeAndViewCountToRedis() {
        //初始化文章浏览|点赞量
        articleService.getArticleLikeMap().forEach(
                (id, likeCount) -> stringRedisTemplate.opsForZSet().add(
                        SystemConstants.ARTICLE_LIKE_COUNT, String.valueOf(id), likeCount));

        articleService.getArticleViewMap().forEach(
                (id, viewCount) -> stringRedisTemplate.opsForZSet().add(
                        SystemConstants.ARTICLE_VIEW_COUNT, String.valueOf(id), viewCount));
    }
}
