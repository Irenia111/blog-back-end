package com.irenia.blog.service;

import com.irenia.blog.prototype.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
