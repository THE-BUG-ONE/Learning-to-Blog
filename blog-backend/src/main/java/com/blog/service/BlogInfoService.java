package com.blog.service;

import cn.hutool.core.date.DateTime;
import com.blog.constants.SystemConstants;
import com.blog.entity.dao.Article;
import com.blog.entity.vo.response.*;
import com.blog.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

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

    public BlogInfoResp getBlogInfo() {
        return new BlogInfoResp(
                articleService.lambdaQuery()
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                        .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE).count(),
                categoryService.count(null),
                siteConfigService.getSiteConfig(),
                tagService.count(null),
                Optional.ofNullable(stringRedisTemplate.opsForValue()
                        .get(SystemConstants.USER_VIEW_COUNT)).orElse(String.valueOf(0)));
    }

    public String about() {
        return siteConfigService.query().one().getAboutMe();
    }

    public void report() {

    }
    //TODO 参数返回缺失
    public BlogBackInfoResp getBlogBackInfo() {
        //获取文章浏览量前5
        Map<Object, Double> articleMap = Objects.requireNonNull(stringRedisTemplate.opsForZSet()
                .reverseRangeWithScores(SystemConstants.ARTICLE_VIEW_COUNT, 0, 4))
                .stream()
                .collect(Collectors.toMap(
                        ZSetOperations.TypedTuple::getValue,
                        ZSetOperations.TypedTuple::getScore));
        return new BlogBackInfoResp(
                articleService.lambdaQuery()
                        .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC).count(),
                getArticleRank(articleMap),
                null, //articleStatisticsList
                categoryService.getCategoryList(),
                messageService.count(),
                BeanCopyUtils.copyBeanList(tagService.list(), TagOptionResp.class),
                userService.count(),
                getUserViewList(),
                Integer.parseInt(Optional.ofNullable(stringRedisTemplate.opsForValue()
                        .get(SystemConstants.USER_VIEW_COUNT)).orElse(String.valueOf(0))));
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

    //获取文章浏览量
    private List<UserViewResp> getUserViewList() {
        List<UserViewResp> userViewRespList = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<String>> viewCounts = stringRedisTemplate.opsForZSet()
                .rangeWithScores(SystemConstants.ARTICLE_VIEW_COUNT, 0, -1);
        String userCount = stringRedisTemplate.opsForValue()
                .get(SystemConstants.USER_WEEK_VIEW_COUNT);
        if (viewCounts != null) {
            viewCounts.forEach(count -> userViewRespList.add(new UserViewResp(
                    DateTime.now(),
                    Objects.requireNonNull(count.getScore()).intValue(),
                    Integer.parseInt(Optional.ofNullable(userCount).orElse("0")))));
        }
        return userViewRespList;
    }
}
