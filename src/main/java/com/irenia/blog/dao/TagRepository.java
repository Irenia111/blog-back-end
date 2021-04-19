package com.irenia.blog.dao;

import com.irenia.blog.prototype.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Type, Long> {
}
