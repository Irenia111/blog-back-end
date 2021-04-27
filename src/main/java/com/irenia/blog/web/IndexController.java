package com.irenia.blog.web;


// import com.irenia.blog.NotFoundException;
import com.irenia.blog.NotFoundException;
import com.irenia.blog.service.BlogService;
import com.irenia.blog.service.TagService;
import com.irenia.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        // throw new NotFoundException("页面未找到");
        model.addAttribute("blogs", blogService.listBlog(pageable));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlog(8));
        model.addAttribute("types", typeService.listTopType(8));
        model.addAttribute("tags", tagService.listTopTag(10));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("queryBlogs", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,
                            Model model) {
        model.addAttribute("blog",
                blogService.getHTMLContentBlog(id));
        return "blog";
    }




}
