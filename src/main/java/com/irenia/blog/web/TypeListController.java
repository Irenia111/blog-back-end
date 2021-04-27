package com.irenia.blog.web;

import com.irenia.blog.prototype.Type;
import com.irenia.blog.service.BlogService;
import com.irenia.blog.service.TypeService;
import com.irenia.blog.vo.BlogQuery;
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
public class TypeListController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/type/{id}")
    public  String type(
            @PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                    Pageable pageable,
            @PathVariable Long id,
            Model model) {
        List<Type> types = typeService.listType();
        //直接访问types页面时，将第一个type的id作为默认id
        if (id == -1) {
            id = types.get(0).getId();
        }
        model.addAttribute("types", types);
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("blogs",
                blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
