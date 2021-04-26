package com.irenia.blog.service;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.dao.TagRepository;
import com.irenia.blog.prototype.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Modifying
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Optional<Tag> getTag(Long id) {
        return tagRepository.findById(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public List<Tag> listTag(String ids) {
        List<Long> idList = getIdList(ids);
        return tagRepository.findAllById(idList);
    }

    @Transactional
    @Override
    public List<Tag> listTopTag(Integer size) {
        Pageable pageable = PageRequest.of(0,size, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return tagRepository.findTop(pageable);
    }

    private List<Long> getIdList(String ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    @Transactional
    @Modifying
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findById(id).orElseThrow(() -> new NotFoundException("tag not found"));
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    @Transactional
    @Modifying
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
