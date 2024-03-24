package com.framework.constants;

public class SystemConstants {
    //文章是普通文章
    public static final int ARTICLE_STATUS_NORMAL = 1;
    //文章是草稿
    public static final int ARTICLE_STATUS_DRAFT = 2;
    //文章是已删除文章
    public static final int ARTICLE_STATUS_DELETE = 3;

    //文章已推荐
    public static final int ARTICLE_IS_RECOMMEND = 1;
    //文章未推荐
    public static final int ARTICLE_NOT_RECOMMEND = 0;

    //首页推荐文章个数
    public static final String ARTICLE_RECOMMEND_NUM = "limit 0,10";
}
