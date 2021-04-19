package com.irenia.blog.service;

import com.irenia.blog.dao.UserRepository;
import com.irenia.blog.prototype.User;
import com.irenia.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUsers(String userName, String passWord) {
        User user = userRepository.findUserByUsernameAndPassword(userName, MD5Utils.code(passWord));
        return user;
    }
}
