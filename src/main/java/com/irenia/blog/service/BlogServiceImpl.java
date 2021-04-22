package com.irenia.blog.service;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.dao.BlogRepository;
import com.irenia.blog.prototype.Blog;
import com.irenia.blog.prototype.Tag;
import com.irenia.blog.prototype.Type;
import com.irenia.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
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
    @Modifying
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional
    @Modifying
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
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        //一开始传入了blog对象，但是网页初始化时，blog不存在，所以采用包装类BlogQuery避免该问题
        //动态查询
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (blog.getTitle() != null && !blog.getTitle().isEmpty()) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Modifying
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
