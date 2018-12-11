package com.donwae.market.dao;

import com.donwae.market.entity.RolePermission;
import com.donwae.market.entity.RolePermissionKey;

import java.util.List;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(RolePermissionKey key);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(RolePermissionKey key);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    List<RolePermission> selectByRoleId(String roleId);
}