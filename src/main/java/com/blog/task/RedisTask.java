package com.blog.task;

import com.blog.service.ArticleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisTask {

    @Resource
    private ArticleService articleService;
//
//    @PostConstruct
//    public void init() {
//        updateArticleLikeAndViewCount();
//    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateArticleLikeAndViewCount() {
        articleService.updateArticleLikeAndViewCount();
        log.info("Redis Task : Update article like and view count");
    }
}
