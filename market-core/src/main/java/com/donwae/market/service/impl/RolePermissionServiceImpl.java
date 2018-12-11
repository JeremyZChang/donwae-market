package com.donwae.market.service.impl;

import com.donwae.market.dao.PermissionMapper;
import com.donwae.market.dao.RolePermissionMapper;
import com.donwae.market.entity.RolePermission;
import com.donwae.market.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 服务实现类
 * 2018/11/09 11:06:48
 * @author Jeremy Zhang
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RolePermission> getPermissionByRoleId(String roleId) {

        return rolePermissionMapper.selectByRoleId(roleId);
    }
}
