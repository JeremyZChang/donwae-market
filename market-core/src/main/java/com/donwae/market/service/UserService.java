package com.donwae.market.service;

import com.donwae.market.entity.User;

/**
 * 服务类
 * 2018/11/09 11:08:42
 * @author Jeremy Zhang
 */
public interface UserService {
    User findByName(String name);
}
