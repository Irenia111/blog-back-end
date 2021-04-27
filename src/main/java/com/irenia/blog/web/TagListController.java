package com.irenia.blog.web;

import com.irenia.blog.prototype.Tag;
import com.irenia.blog.service.BlogService;
import com.irenia.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagListController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;

    @GetMapping("/tag/{id}")
    public String tag(
            @PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                    Pageable pageable,
            @PathVariable Long id,
            Model model) {
        List<Tag> tags = tagService.listTopTag(1000);//这里要找博客最多的tag
        //直接访问types页面时，将第一个type的id作为默认id
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("blogs",
                blogService.listBlog(id, pageable));
        model.addAttribute("activeTypeId", id);
        return "tags";
    }
}
