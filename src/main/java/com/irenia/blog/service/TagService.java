package com.irenia.blog.service;

import com.irenia.blog.prototype.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Tag saveTag(Tag type);

    Optional<Tag> getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTopTag(Integer size);

    Tag updateTag(Long id, Tag type);

    void deleteTag(Long id);
}
