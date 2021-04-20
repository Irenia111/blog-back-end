package com.irenia.blog.dao;

import com.irenia.blog.prototype.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
