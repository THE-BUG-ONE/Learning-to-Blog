package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Role;
import com.framework.entity.vo.request.DisableReq;
import com.framework.entity.vo.request.RoleBackReq;
import com.framework.entity.vo.request.RoleReq;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.RoleResp;

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
}

