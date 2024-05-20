package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.DisableReq;
import com.framework.entity.vo.request.RoleBackReq;
import com.framework.entity.vo.request.RoleReq;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.RoleResp;
import com.framework.service.RoleService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @SystemLog(businessName = "添加角色")
    @PostMapping("/add")
    public Result<?> addRole(@RequestBody @Valid RoleReq roleReq) {
        roleService.addRole(roleReq);
        return Result.success();
    }

    @SystemLog(businessName = "修改角色状态")
    @PutMapping("/changeStatus")
    public Result<?> changeRoleStatus(@RequestBody @Valid DisableReq disableReq) {
        roleService.changeRoleStatus(disableReq);
        return Result.success();
    }

    @SystemLog(businessName = "删除角色")
    @DeleteMapping("/delete")
    public Result<?> deleteRole(@RequestBody @NotNull List<String> roleIdList) {
        roleService.deleteRole(roleIdList);
        return Result.success();
    }

    @SystemLog(businessName = "查看后台角色列表")
    @GetMapping("/list")
    public Result<PageResult<RoleResp>> getBackRoleList(@RequestBody @Valid RoleBackReq roleBackReq) {
        PageResult<RoleResp> res = roleService.getBackRoleList(roleBackReq);
        return Result.result(res);
    }
}
