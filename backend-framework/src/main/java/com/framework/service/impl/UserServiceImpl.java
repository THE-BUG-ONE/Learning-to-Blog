package com.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.constants.SystemConstants;
import com.framework.entity.dao.LoginUser;
import com.framework.entity.dao.User;
import com.framework.mapper.UserMapper;
import com.framework.service.UserService;
import com.framework.utils.FileUtils;
import com.framework.utils.WebUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

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

    @Override
    @Transactional
    public String updateUserAvatar(LoginUser loginUser, MultipartFile file) {
        try {
            //判断文件类型是否为图片
            if (!Arrays.asList(SystemConstants.IMAGE_CONTENT_TYPE).contains(file.getContentType()))
                throw new RuntimeException();
            String path = file.getOriginalFilename();
            //TODO 文件上传未完成
            int userId = loginUser.getUser().getId();
            if (!this.lambdaUpdate()
                    .eq(User::getId, userId)
                    .set(User::getAvatar, path)
                    .update()) {
                throw new RuntimeException();
            }
            //返回文件保存路径
            return path;
        } catch (Exception e) {
            throw new RuntimeException("用户头像修改异常");
        }
    }
}

