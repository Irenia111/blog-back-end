package com.irenia.blog.dao;

import com.irenia.blog.prototype.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    Optional<Blog> findById(Long id);
    Page<Blog> listBlog(Pageable pageable, Blog blog);
}
