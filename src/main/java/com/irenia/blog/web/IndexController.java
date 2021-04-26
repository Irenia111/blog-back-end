package com.irenia.blog.web;


// import com.irenia.blog.NotFoundException;
import com.irenia.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        // throw new NotFoundException("页面未找到");
        System.out.println("------index------");
        model.addAttribute("blogs", blogService.listBlog(pageable));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlog(8));
        return "index";
    }
}
