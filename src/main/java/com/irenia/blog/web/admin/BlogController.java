package com.irenia.blog.web.admin;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.prototype.Blog;
import com.irenia.blog.service.BlogService;
import com.irenia.blog.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/admin-blog-edit";
    private static final String LIST = "admin/admin-blog";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogList(
            @PageableDefault(
                    size = 10,
                    sort = {"published"},
                    direction = Sort.Direction.DESC) Pageable pageable,
            BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8,
            sort = {"published"},
            direction = Sort.Direction.DESC)
                                 Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";//返回的是一个fragment
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("blog", new Blog());
        return "admin/admin-blog-edit";
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,
                            Model model) {
        model.addAttribute("blog",
                blogService.getBlog(id).orElseThrow(() -> new NotFoundException("blog can not be found")));
        return "admin/admin-blog-edit";
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("success", "删除成功");
        return "redirect:/admin/blogs";
    }
}
