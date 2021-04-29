package com.irenia.blog.web;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.prototype.Comment;
import com.irenia.blog.prototype.User;
import com.irenia.blog.service.BlogService;
import com.irenia.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        System.out.println("aaaaa");
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment) {
        System.out.println("ssss");
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId)
                .orElseThrow(() -> new NotFoundException("blog not found")));
       commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
