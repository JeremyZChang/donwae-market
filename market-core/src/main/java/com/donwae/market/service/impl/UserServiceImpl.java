package com.donwae.market.service.impl;

import com.donwae.market.dao.UserMapper;
import com.donwae.market.entity.User;
import com.donwae.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 * 2018/11/09 11:07:17
 * @author Jeremy Zhang
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String name) {
        return userMapper.selectByName(name);
    }
}

