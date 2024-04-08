package com.framework.service;

import com.framework.constants.SystemConstants;
import com.framework.entity.dao.Article;
import com.framework.entity.vo.response.ArticleRankResp;
import com.framework.entity.vo.response.BlogBackInfoResp;
import com.framework.entity.vo.response.BlogInfoResp;
import com.framework.entity.vo.response.TagOptionResp;
import com.framework.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    @Lazy
    @Resource
    private UserService userService;

    @Lazy
    @Resource
    private MessageService messageService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public BlogInfoResp getBlogInfo() {
        return new BlogInfoResp(
                articleService.lambdaQuery()
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                        .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE).count(),
                categoryService.count(null),
                siteConfigService.getSiteConfig(),
                tagService.count(null),
                Optional.ofNullable(stringRedisTemplate.opsForValue()
                        .get(SystemConstants.BLOG_VIEW_COUNT)).orElse(String.valueOf(0)));
    }

    public String about() {
        return siteConfigService.query().one().getAboutMe();
    }

    public void report() {
        stringRedisTemplate.opsForValue().increment(SystemConstants.BLOG_VIEW_COUNT, 1);
    }

    public BlogBackInfoResp getBlogBackInfo() {
        Map<Object, Double> articleMap = Objects.requireNonNull(redisTemplate.opsForZSet()
                .reverseRangeWithScores(SystemConstants.ARTICLE_VIEW_COUNT, 0, 4))
                .stream().collect(Collectors.toMap(
                        ZSetOperations.TypedTuple::getValue,
                        ZSetOperations.TypedTuple::getScore));
        return new BlogBackInfoResp(
                articleService.lambdaQuery()
                        .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC).count(),
                this.getArticleRank(articleMap), //articleRankVOList
                null, //articleStatisticsList
                categoryService.getCategoryList(),
                messageService.count(), //messageCount
                BeanCopyUtils.copyBeanList(tagService.list(), TagOptionResp.class),
                userService.count(),
                null, //userViewVOList
                Integer.parseInt(Optional.ofNullable(stringRedisTemplate.opsForValue()
                        .get(SystemConstants.BLOG_VIEW_COUNT)).orElse(String.valueOf(0))));
    }

    //获取浏览量前5的文章
    private List<ArticleRankResp> getArticleRank(Map<Object, Double> articleMap) {
        if (articleMap.isEmpty()) return null;
        //获取文章id列表
        List<Integer> articleIdList = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, val) -> articleIdList.add((Integer) key));
        //获取文章列表
        List<Article> articleList = articleService.lambdaQuery()
                .select(Article::getArticleTitle, Article::getId)
                .in(Article::getId, articleIdList).list();
        //封装articleList为ArticleRankRespList
        return articleList.stream()
                .map(article -> ArticleRankResp.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankResp::getViewCount)
                .reversed()).collect(Collectors.toList());
    }
}
