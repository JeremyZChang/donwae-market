package com.donwae.market.service;

import com.donwae.market.entity.RolePermission;

import java.util.List;

/**
 * 服务类
 * 2018/11/09 11:08:06
 * @author Jeremy Zhang
 */
public interface RolePermissionService {
    /**
     * //TODO
     * 2018/11/13 16:03:04
     * @author Jeremy Zhang
     */
    public List<RolePermission> getPermissionByRoleId(String roleId);
}
