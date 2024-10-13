package com.framework.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.entity.dao.Role;
import com.framework.entity.vo.request.DisableReq;
import com.framework.entity.vo.request.RoleBackReq;
import com.framework.entity.vo.request.RoleReq;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.RoleResp;
import com.framework.mapper.RoleMapper;
import com.framework.service.RoleService;
import com.framework.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2024-05-19 14:16:04
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    @Transactional
    public void addRole(RoleReq req) {
        String id = req.getId();
        String roleDesc = req.getRoleDesc();
        String roleName = req.getRoleName();
        Integer isDisable = req.getIsDisable();

        Date date = DateTime.now();

        if (!save(new Role(id, roleName, roleDesc, isDisable, date, null)))
            throw new RuntimeException("添加角色异常:[添加角色菜单错误,未知异常]");
    }

    @Override
    @Transactional
    public void changeRoleStatus(DisableReq req) {
        Integer id = req.getId();
        Integer isDisable = req.getIsDisable();

        if (!lambdaUpdate()
                .eq(Role::getId, id)
                .set(Role::getIsDisable, isDisable)
                .update())
            throw new RuntimeException("修改角色状态异常:[未知异常]");
    }

    @Override
    @Transactional
    public void deleteRole(List<String> roleIdList) {
        if (!removeBatchByIds(roleIdList))
            throw new RuntimeException("删除角色异常:[未知异常]");
    }

    @Override
    public PageResult<RoleResp> getBackRoleList(RoleBackReq req) {
        Integer current = req.getCurrent();
        Integer size = req.getSize();
        Integer isDisable = req.getIsDisable();
        String keyword = req.getKeyword();

        List<RoleResp> respList = BeanCopyUtils.copyBeanList(page(new Page<>(current, size), lambdaQuery()
                .eq(isDisable != null, Role::getIsDisable, isDisable)
                .like(keyword != null, Role::getRoleName, keyword)
                .getWrapper()).getRecords(), RoleResp.class);
        return new PageResult<>(respList.size(), respList);
    }

    @Override
    @Transactional
    public void updateRole(RoleReq roleReq) {
    }
}

