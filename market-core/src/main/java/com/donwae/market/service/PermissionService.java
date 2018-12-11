package com.donwae.market.service;

import com.donwae.market.entity.Permission;
import com.donwae.market.entity.Role;

import java.util.List;

/**
 * 服务类
 * 2018/11/09 11:07:57
 * @author Jeremy Zhang
 */
public interface PermissionService {
    /**
     * //TODO
     * 2018/11/13 15:43:24
     * @author Jeremy Zhang
     */
    List<Permission> getPermissionByRole(Role role);
}
