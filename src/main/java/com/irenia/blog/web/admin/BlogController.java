package com.irenia.blog.web.admin;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.prototype.Blog;
import com.irenia.blog.prototype.User;
import com.irenia.blog.service.*;
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

import javax.servlet.http.HttpSession;

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
    @Autowired
    private FlagService flagService;

    @GetMapping("/blogs")
    public String blogList(@PageableDefault(size = 8,
            sort = {"updateTime"},
            direction = Sort.Direction.DESC)
                                   Pageable pageable,
                           BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("published", blogService.listBlog(pageable, blog));
        model.addAttribute("unpublished", blogService.listUnpublishedBlog());
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8,
            sort = {"published"},
            direction = Sort.Direction.DESC)
                                 Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("published", blogService.listBlog(pageable, blog));
        return "admin/admin-blog::blogList";//??????????????????fragment
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("flags", flagService.listFlag());
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()).orElseThrow(() -> new NotFoundException("type not found")));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        blog.setFlag(flagService.getFlag(blog.getFlag().getId()).orElseThrow(() -> new NotFoundException("flag not found")));
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null) {
            attributes.addFlashAttribute("error", "????????????");
        } else {
            attributes.addFlashAttribute("success", "????????????");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,
                            Model model) {
        Blog b = blogService.getBlog(id).orElseThrow(() -> new NotFoundException("blog can not be found"));
        b.init();//??????tags
        model.addAttribute("blog", b);
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("flags", flagService.listFlag());
        return INPUT;
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("success", "????????????");
        return "redirect:/admin/blogs";
    }
}
