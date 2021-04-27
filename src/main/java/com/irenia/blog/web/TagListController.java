package com.irenia.blog.web;

import com.irenia.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TagListController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tag")
    public  String tag(Model model){
        model.addAttribute("tags",
                tagService.listTag());
        return "tags";
    }
}
