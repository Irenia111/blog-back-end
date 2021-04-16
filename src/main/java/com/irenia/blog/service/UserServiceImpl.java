package com.irenia.blog.service;

import com.irenia.blog.dao.UserRepository;
import com.irenia.blog.prototype.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUsers(String userName, String passWord) {
        User user = userRepository.findUserByUsernameAndPassword(userName, passWord);
        return user;
    }
}
