package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.entity.dao.Article;
import com.blog.entity.dao.User;
import com.blog.entity.dao.UserRole;
import com.blog.entity.vo.request.*;
import com.blog.entity.vo.response.*;
import com.blog.mapper.RoleMapper;
import com.blog.mapper.UserMapper;
import com.blog.service.ArticleService;
import com.blog.service.BlogLoginService;
import com.blog.service.UserRoleService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.FileUtils;
import com.blog.utils.WebUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2024-04-08 14:09:04
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private WebUtils webUtils;

    @Resource
    private FileUtils fileUtils;

    @Lazy
    @Resource
    private BlogLoginService blogLoginService;

    @Lazy
    @Resource
    private ArticleService articleService;

    @Lazy
    @Resource
    private UserRoleService userRoleService;

    @Lazy
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public String updateUserAvatar(MultipartFile file) {
        //判断文件类型是否为图片
        if (!Arrays.asList(SystemConstants.IMAGE_CONTENT_TYPE).contains(file.getContentType()))
            throw new RuntimeException("文件类型错误");
        String filePath = file.getOriginalFilename();
        //TODO 文件上传未完成
        if (!lambdaUpdate()
                .eq(User::getId, getUserId())
                .set(User::getAvatar, filePath)
                .set(User::getUpdateTime, DateTime.now())
                .update())
            throw new RuntimeException("头像修改异常");
        //返回文件保存路径
        return filePath;
    }

    @Override
    @Transactional
    public void updateEmail(EmailReq emailReq) {
        String msg = emailCodeCheck(emailReq.getUsername(), emailReq.getCode(), SystemConstants.EMAIL_RESET_CODE);
        if (msg != null)
            throw new RuntimeException(msg);
        if (!lambdaUpdate()
                .eq(User::getId, getUserId())
                .set(User::getEmail, emailReq.getUsername())
                .set(User::getUpdateTime, DateTime.now())
                .update())
            throw new RuntimeException("邮箱修改异常");
        //邮箱修改后应退出登录
        blogLoginService.logout();
    }

    @Override
    public String emailCodeCheck(String username, String code, String type) {
        if (type.equals(SystemConstants.REGISTER_CODE) && lambdaQuery().eq(User::getEmail, username).exists())
            return "此邮箱地址已被注册";
        if (!Boolean.TRUE.equals(stringRedisTemplate.hasKey(SystemConstants.VERIFY_EMAIL_DATA + username)))
            return "验证码不存在";
        if (!code.equals(stringRedisTemplate.opsForValue().get(SystemConstants.VERIFY_EMAIL_DATA + username)))
            return "验证码错误";
        return null;
    }

    @Override
    public UserInfoResp getUserInfo() {
        User user = webUtils.getRequestUser();
        return BeanCopyUtils.copyBean(user, UserInfoResp.class)
                .setRoles(roleMapper.getRoleList(user.getId()));
    }

    @Override
    @Transactional
    public void updateInfo(UserInfoReq userInfoReq) {
        String intro = userInfoReq.getIntro();
        String nickname = userInfoReq.getNickname();
        if (!lambdaUpdate()
                .eq(User::getId, getUserId())
                .set(intro != null, User::getIntro, intro)
                .set(User::getNickname, nickname)
                .set(User::getUpdateTime, DateTime.now())
                .update())
            throw new RuntimeException("用户信息修改异常");
    }

    @Override
    @Transactional
    public void updatePassword(UserReq userReq) {
        String msg = emailCodeCheck(userReq.getUsername(), userReq.getCode(), SystemConstants.PASSWORD_RESET_CODE);
        if (msg != null)
            throw new RuntimeException(msg);
        String password = encoder.encode(userReq.getPassword());
        if (!lambdaUpdate()
                .eq(User::getId, getUserId())
                .set(User::getPassword, password)
                .set(User::getUpdateTime, DateTime.now())
                .update())
            throw new RuntimeException("用户密码修改异常");
        //密码修改后应退出登录
        blogLoginService.logout();
    }

    @Override
    @Transactional
    public void changeUserStatus(DisableReq disableReq) {
        if (!lambdaUpdate()
                .eq(User::getId, disableReq.getId())
                .set(User::getIsDisable, disableReq.getIsDisable())
                .set(User::getUpdateTime, DateTime.now())
                .update())
            throw new RuntimeException("修改用户状态异常:[未知异常]");
    }

    @Override
    public UserBackInfoResp getBackUserInfo() {
        return BeanCopyUtils.copyBean(lambdaQuery()
                .eq(User::getId, getUserId())
                .one(), UserBackInfoResp.class)
                .setRoleList(roleMapper.getRoleList(getUserId()));
    }

    @Override
    public PageResult<UserBackResp> getBackUserList(UserBackReq req) {
        Integer page = req.getPage();
        Integer limit = req.getLimit();
        String keyword = req.getKeyword();
        Integer loginType = req.getLoginType();

        List<UserBackResp> respList = BeanCopyUtils.copyBeanList(page(new Page<>(page, limit), lambdaQuery()
                .like(keyword != null, User::getNickname, keyword)
                .eq(loginType != null, User::getLoginType, loginType)
                .getWrapper()).getRecords(), UserBackResp.class)
                .stream()
                .peek(user -> user.setRoleList(getUserRoleList(user.getId())))
                .toList();
        return new PageResult<>(respList.size(), respList);
    }

    @Override
    public List<UserRoleResp> getUserRoleList() {
        return getUserRoleList(getUserId());
    }

    @Override
    @Transactional
    public void updateUser(UserRoleReq req) {
        Integer id = req.getId();
        String nickname = req.getNickname();
        List<UserRole> userRoleList = req.getRoleIdList()
                .stream()
                .map(roleId -> new UserRole(id, roleId))
                .toList();

        if (!lambdaUpdate()
                .eq(User::getId, id)
                .set(User::getNickname, nickname)
                .set(User::getUpdateTime, DateTime.now())
                .update() ||
        !userRoleService.remove(userRoleService.lambdaQuery().eq(UserRole::getUserId, id)) ||
        !userRoleService.saveBatch(userRoleList))
            throw new RuntimeException("修改用户异常:[删除用户角色错误,保存用户角色错误,未知错误]");
    }

    @Override
    public UserMenuResp getUserMenu() {
//        //子菜单列表
//        private List<UserMenuResp> children;
//        //菜单组件
//        private String component;
//        //路由其他信息Response
//        private MeteResp mete;
//        //菜单名称
//        private String name;
//        //路由地址
//        private String path;
//        //重定向地址
//        private String redirect;

        int userId = getUserId();


        return null;
    }

    @Override
    public List<UserOptionResp> getUserOptionList(String username) {
        return baseMapper.getUserOptionList(username);
    }

    private List<UserRoleResp> getUserRoleList(Integer userId) {
        return roleMapper.getUserRoleList(userId);
    }

    private int getUserId() {
        return webUtils.getRequestUser().getId();
    }
}

