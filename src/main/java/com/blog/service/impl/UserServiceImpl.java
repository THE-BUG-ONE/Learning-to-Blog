package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.entity.dao.User;
import com.blog.entity.dao.UserRole;
import com.blog.entity.vo.request.*;
import com.blog.entity.vo.response.*;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.RoleMapper;
import com.blog.mapper.UserMapper;
import com.blog.service.BlogLoginService;
import com.blog.service.UserRoleService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.FileUtils;
import com.blog.utils.PageUtils;
import com.blog.utils.WebUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

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
    private ArticleMapper articleMapper;

    @Lazy
    @Resource
    private UserRoleService userRoleService;

    @Lazy
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private PageUtils pageUtils;

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
                .setRoles(roleMapper.getUserRoleNameList(user.getId()))
                .setArticleCount(articleMapper.countArticleByUserId(user.getId()));
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
                .setRoleList(roleMapper.getUserRoleNameList(getUserId()));
    }

    @Override
    public PageResult<UserBackResp> getBackUserList(PageReq req) {
        pageUtils.setPage(req);
        List<UserBackResp> backUserList = baseMapper.getBackUserList(req);
        return new PageResult<>(backUserList.size(), backUserList);
    }

    @Override
    public List<UserRoleResp> getUserRoleList() {
        return getUserRoleList(getUserId());
    }

    @Override
    @Transactional
    public UserBackResp updateUser(UserInfoReq req) {
        try {
            if (!lambdaUpdate()
                    .eq(User::getId, req.getId())
                    .set(req.getNickname() != null, User::getNickname, req.getNickname())
                    .set(req.getUsername() != null, User::getUsername, req.getUsername())
                    .set(req.getIntro() != null, User::getIntro, req.getIntro())
                    .set(req.getIsDisable() != null, User::getIsDisable, req.getIsDisable())
                    .update() || (req.getRoleId() != null && !userRoleService.lambdaUpdate()
                    .eq(UserRole::getUserId, req.getId())
                    .set(UserRole::getRoleId, req.getRoleId())
                    .update()))
                throw new RuntimeException();
            return baseMapper.getBackUserById(req.getId());
        } catch (Exception e) {
            throw new RuntimeException("修改用户异常:[删除用户角色错误,保存用户角色错误,未知错误]");
        }
    }
    
    @Override
    public List<UserOptionResp> getUserOptionList(String username) {
        return baseMapper.getUserOptionList(username);
    }

    @Override
    @Transactional
    public void deleteUser(List<Integer> userIdList) {
        try {
            //仅允许删除普通用户与测试用户
            List<Integer> idList = userRoleService.lambdaQuery()
                    .select(UserRole::getUserId)
                    .in(UserRole::getRoleId, Arrays.asList(
                            SystemConstants.USER_ROLE_USER,
                            SystemConstants.USER_ROLE_TEST))
                    .list()
                    .stream().map(UserRole::getUserId).toList();
            if (!removeBatchByIds(userIdList.stream().filter(idList::contains).toList()))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("删除用户异常:" + e.getMessage());
        }
    }

    private List<UserRoleResp> getUserRoleList(Integer userId) {
        return roleMapper.getUserRoleList(userId);
    }

    private int getUserId() {
        return webUtils.getRequestUser().getId();
    }
}

