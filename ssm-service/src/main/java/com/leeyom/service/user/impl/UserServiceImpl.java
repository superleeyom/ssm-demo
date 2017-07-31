package com.leeyom.service.user.impl;

import com.leeyom.dao.user.UserMapper;
import com.leeyom.pojo.User;
import com.leeyom.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User getUserById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
