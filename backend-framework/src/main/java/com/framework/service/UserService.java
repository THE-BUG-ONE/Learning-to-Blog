package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.LoginUser;
import com.framework.entity.dao.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2024-04-08 14:09:04
 */
public interface UserService extends IService<User> {

    String updateUserAvatar(LoginUser loginUser, MultipartFile file);
}

