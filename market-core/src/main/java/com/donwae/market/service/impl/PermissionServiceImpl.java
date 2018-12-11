package com.donwae.market.service.impl;

import com.donwae.market.dao.PermissionMapper;
import com.donwae.market.entity.Permission;
import com.donwae.market.entity.Role;
import com.donwae.market.entity.RolePermission;
import com.donwae.market.service.PermissionService;
import com.donwae.market.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 * 2018/11/09 11:06:13
 * @author Jeremy
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionByRole(Role role) {
        List<RolePermission> rolePermissions = rolePermissionService.getPermissionByRoleId(role.getId());

        List<Permission> permissions = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions){
            Permission permission = permissionMapper.selectByPrimaryKey(rolePermission.getPermissionId());
            if(permission != null)
                permissions.add(permission);
        }
        return permissions;
    }
}
