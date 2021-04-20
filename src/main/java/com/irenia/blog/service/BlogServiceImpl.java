package com.irenia.blog.service;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.dao.BlogRepository;
import com.irenia.blog.prototype.Blog;
import com.irenia.blog.prototype.Tag;
import com.irenia.blog.prototype.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog oldBlog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("blog not found"));
        BeanUtils.copyProperties(blog, oldBlog);
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Optional<Blog> getBlog(Long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        //动态查询
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                //处理动态查询的条件
                List<Predicate> predicateList = new ArrayList<>();
                //title的like查询
                if (!blog.getTitle().isEmpty() && blog.getTitle() != null) {
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"),
                            "%" + blog.getTitle() + "%"));
                }
                //根据type的id进行查找
                if (blog.getType().getId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),
                            blog.getType().getId()));
                }

                //根据type的recommend进行查找
                if (blog.isRecommend()) {
                    predicateList.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),
                            blog.isRecommend()));
                }
                criteriaQuery.where(predicateList.toArray(new Predicate[0]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
