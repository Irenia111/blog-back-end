package com.irenia.blog.service;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.dao.BlogRepository;
import com.irenia.blog.prototype.Blog;
import com.irenia.blog.prototype.Type;
import com.irenia.blog.util.MarkdownUtils;
import com.irenia.blog.util.MyBeanUtils;
import com.irenia.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.Date;
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
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Modifying
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog oldBlog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("blog not found"));
        //这里会存在更新无法保存的状况，因为源对象有null值，在使用BeanUtils来copy时null值会覆盖目标对象的同名字段属性值
        //创建时间会为null，还有view等数据
        //传入为null的属性，null值属性不做设置
        BeanUtils.copyProperties(blog, oldBlog, MyBeanUtils.getNullPropertyNames(blog));
        oldBlog.setUpdateTime(new Date());
        return blogRepository.save(oldBlog);
    }

    @Transactional
    @Override
    public Optional<Blog> getBlog(Long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    @Override
    public Blog getHTMLContentBlog(Long id) {
        Blog blog = blogRepository
                .findById(id)
                .orElseThrow(()->new NotFoundException("blog not found"));
        Blog HTMLBlog = new Blog();
        BeanUtils.copyProperties(blog,HTMLBlog);
        String content = HTMLBlog.getContent();
        HTMLBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogRepository.updateViews(id);
        return HTMLBlog;
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
                predicates.add(cb.equal(root.<Boolean>get("published"), true));
                if (blog.getTitle() != null && !blog.getTitle().isEmpty()) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
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
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Blog> listUnpublishedBlog() {
        //一开始传入了blog对象，但是网页初始化时，blog不存在，所以采用包装类BlogQuery避免该问题
        //动态查询
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.<Boolean>get("published"), false));
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        });
    }

    @Override
    public List<Blog> listRecommendBlog(Integer size) {
        Pageable pageable = PageRequest.of(0,size,Sort.by(Sort.Direction.DESC, "views"));
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        }, pageable);
    }

    @Transactional
    @Modifying
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
