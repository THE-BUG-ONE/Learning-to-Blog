package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Role;
import com.blog.entity.vo.request.DisableReq;
import com.blog.entity.vo.request.RoleBackReq;
import com.blog.entity.vo.request.RoleReq;
import com.blog.entity.vo.response.PageResult;
import com.blog.entity.vo.response.RoleResp;

import java.util.List;

/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2024-05-19 14:16:04
 */
public interface RoleService extends IService<Role> {

    void addRole(RoleReq req);

    void changeRoleStatus(DisableReq req);

    void deleteRole(List<String> roleIdList);

    PageResult<RoleResp> getBackRoleList(RoleBackReq req);

    void updateRole(RoleReq roleReq);
}

