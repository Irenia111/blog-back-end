package com.irenia.blog.dao;

import com.irenia.blog.prototype.User;
import org.springframework.data.jpa.repository.JpaRepository;

//继承Jpa的User数据库
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsernameAndPassword(String name, String password);
}
