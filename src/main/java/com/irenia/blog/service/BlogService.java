package com.irenia.blog.service;

import com.irenia.blog.prototype.Blog;
import com.irenia.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    Optional<Blog> getBlog(Long id);
    Blog getHTMLContentBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    List<Blog> listUnpublishedBlog();
    List<Blog> listRecommendBlog(Integer size);
    Page<Blog> listBlog(String query,Pageable pageable);
    Page<Blog> listBlog(Long id,Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
