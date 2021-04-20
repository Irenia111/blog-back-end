package com.irenia.blog.service;

import com.irenia.blog.prototype.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BlogService {
    Optional<Blog> getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, Blog blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
