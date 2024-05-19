package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.User;
import com.framework.entity.vo.request.DisableReq;
import com.framework.entity.vo.request.EmailReq;
import com.framework.entity.vo.request.UserInfoReq;
import com.framework.entity.vo.request.UserReq;
import com.framework.entity.vo.response.UserInfoResp;
import org.springframework.web.multipart.MultipartFile;

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
}

