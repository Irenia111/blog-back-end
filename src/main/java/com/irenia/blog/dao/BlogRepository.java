package com.irenia.blog.dao;

import com.irenia.blog.prototype.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog>{
}
