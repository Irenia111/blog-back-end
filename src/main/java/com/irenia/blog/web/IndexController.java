package com.irenia.blog.web;


import com.irenia.blog.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
            throw new NotFoundException("页面未找到");
    }
}
