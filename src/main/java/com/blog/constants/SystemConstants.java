package com.blog.constants;

public class SystemConstants {

    //发表文章
    public static final int ARTICLE_STATUS_PUBLIC = 1;
    //草稿文章
    public static final int ARTICLE_STATUS_DRAFT = 2;

    //文章已推荐
    public static final int ARTICLE_IS_RECOMMEND = 1;
    //文章未推荐
    public static final int ARTICLE_NOT_RECOMMEND = 0;

    //文章已置顶
    public static final int ARTICLE_IS_TOP = 1;
    //文章未置顶
    public static final int ARTICLE_NOT_TOP = 0;

    //留言已通过
    public static final int MESSAGE_IS_CHECKED = 1;
    //留言未通过
    public static final int MESSAGE_NOT_CHECKED = 0;

    //根留言ID
    public static final int MESSAGE_ROOT = 0;
    //父留言ID
    public static final int MESSAGE_PARENT = 0;

    //评论已通过
    public static final int COMMENT_IS_CHECKED = 1;

    //说说已公开
    public static final int TALK_IS_PUBLIC = 1;

    //用户点赞文章列表
    public static final String USER_ARTICLE_LIKE = "user:article:like:";

    //文章ID列表
    public static final String ARTICLE_LIST = "article:id:list";


    //用户总访问量
    public static final String USER_VIEW_COUNT = "user:view:count:";
    //用户一周访问量
    public static final String USER_WEEK_VIEW_COUNT = "user:view:count:";
    //文章点赞量
    public static final String ARTICLE_LIKE_COUNT = "article:like:count:";
    //文章访问量
    public static final String ARTICLE_VIEW_COUNT = "article:view:count:";
    //评论点赞量
    public static final String COMMENT_LIKE_COUNT = "comment:like:count:";

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

    //用户权限:管理员
    public static final Integer USER_ROLE_ADMIN = 1;
    //用户权限:普通用户
    public static final Integer USER_ROLE_USER = 2;
    //用户权限:测试账号
    public static final Integer USER_ROLE_TEST = 3;

    //已登录用户列表
    public static final String LOGGED_USER_ID = "logged:user:id:";

    //跨域过滤器优先度
    public static final int ORDER_CORS = -102;

    //测试账号ID
    public static final int TEST_ID = 3;

    //用户已禁用
    public static final int USER_DISABLE_T = 1;
    //用户未禁用
    public static final int USER_DISABLE_F = 0;

    //用户默认头像
    public static final String USER_DISABLE_P = "https://tse4-mm.cn.bing.net/th/id/OIP-C.u8sQ4TZBtQP0_xabZPONLQHaEb?rs=1&pid=ImgDetMain";
}
