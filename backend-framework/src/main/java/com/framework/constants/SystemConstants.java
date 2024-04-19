package com.framework.constants;

public class SystemConstants {

    //主键
    public static final String TABLE_PRIMARY = "id";



    //公开文章
    public static final int ARTICLE_STATUS_PUBLIC = 1;
    //私密文章
    public static final int ARTICLE_STATUS_PRIVATE = 2;
    //评论可见文章
    public static final int ARTICLE_STATUS_VISIBLE = 3;

    //文章已推荐
    public static final int ARTICLE_IS_RECOMMEND = 1;
    //文章未推荐
    public static final int ARTICLE_NOT_RECOMMEND = 0;

    //文章已置顶
    public static final int ARTICLE_IS_TOP = 1;
    //文章未置顶
    public static final int ARTICLE_NOT_TOP = 0;

    //文章已删除
    public static final int ARTICLE_IS_DELETE = 1;
    //文章未删除
    public static final int ARTICLE_NOT_DELETE = 0;

    //首页推荐文章个数
    public static final String ARTICLE_RECOMMEND_NUM = "limit 0,10";









    //分类当前正常
    public static final int CATEGORY_STATUS_NORMAL = 0;
    //分类当前禁用
    public static final int CATEGORY_STATUS_DISABLE = 1;


    //博客访问量
    public static final String BLOG_VIEW_COUNT = "blog_view_count";
    //文章点赞量
    public static final String ARTICLE_LIKE_COUNT = "article_like_count";
    //文章访问量
    public static final String ARTICLE_VIEW_COUNT = "article_view_count";
    //查询一条
    public static final String LAST_LIMIT_1 = "limit 1";

    //JWTRedisKey
    public static final String JWT_REDIS_KEY = "blogLogin:";
    //JWT密钥
    public static final String JWT_KEY = "abcdefg";
    //JWTHead
    public static final String JWT_HEAD = "Bearer ";
    //JWT过期时间
    public static final int JWT_EXPIRE = 3;
    //JWT黑名单
    public static final String JWT_BLACK_LIST = "jwt:blackList:";
}
