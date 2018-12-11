package com.donwae.market.dao;

import com.donwae.market.entity.UserRole;
import com.donwae.market.entity.UserRoleKey;

import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(UserRoleKey key);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    List<UserRole> selectByUserId(String userId);
}