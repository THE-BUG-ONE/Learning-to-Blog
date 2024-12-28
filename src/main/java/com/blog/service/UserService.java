package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.User;
import com.blog.entity.vo.request.*;
import com.blog.entity.vo.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2024-04-08 14:09:04
 */
public interface UserService extends IService<User> {

    String updateUserAvatar(MultipartFile file);

    void updateEmail(EmailReq emailReq);

    String emailCodeCheck(String username, String code, String type);

    UserInfoResp getUserInfo();

    void updateInfo(UserInfoReq userInfoReq);

    void updatePassword(UserReq userReq);

    void changeUserStatus(DisableReq disableReq);

    UserBackInfoResp getBackUserInfo();

    PageResult<UserBackResp> getBackUserList(UserBackReq userBackReq);

    List<UserRoleResp> getUserRoleList();

    void updateUser(UserRoleReq req);

    UserMenuResp getUserMenu();

    List<UserOptionResp> getUserOptionList(String username);
}
