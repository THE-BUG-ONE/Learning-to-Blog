package com.framework.constants;

public class SystemConstants {

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

    //留言已通过
    public static final int MESSAGE_IS_CHECKED = 1;
    //留言未通过
    public static final int MESSAGE_NOT_CHECKED = 0;

    //评论已通过
    public static final int COMMENT_IS_CHECKED = 1;

    //首页推荐文章个数
    public static final String ARTICLE_RECOMMEND_NUM = "limit 0,10";
    //最新评论个数
    public static final String COMMENT_NEW_NUM = "limit 0,10";


    //博客访问量
    public static final String BLOG_VIEW_COUNT = "blog_view_count:";
    //文章点赞量
    public static final String ARTICLE_LIKE_COUNT = "article_like_count:";
    //文章访问量
    public static final String ARTICLE_VIEW_COUNT = "article_view_count:";
    //评论点赞量
    public static final String COMMENT_LIKE_COUNT = "comment_like_count:";
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

    //验证邮箱的限制IP
    public static final String VERIFY_EMAIL_LIMIT = "verify:email:limit:";
    //验证邮箱的内容
    public static final String VERIFY_EMAIL_DATA = "verify:email:data:";

    //限流封禁IP
    public static final String FLOW_LIMIT_BLOCK = "flow:block";
    //限流IP计数
    public static final String FLOW_LIMIT_COUNTER = "flow:counter";

    //限流过滤器优先度
    public static final int ORDER_LIMIT = -101;

    //图片格式类型
    public static final String[] IMAGE_CONTENT_TYPE = {"image/jpeg", "image/jpg", "image/png"};
    //头像文件路径
    public static final String AVATAR_PATH = "static/upload/avatar/";

    //验证码类型为注册
    public static final String REGISTER_CODE = "register";
    //验证码类型为邮箱重置
    public static final String EMAIL_RESET_CODE = "emailReset";
    //验证码类型为密码重置
    public static final String PASSWORD_RESET_CODE = "passwordReset";
}
