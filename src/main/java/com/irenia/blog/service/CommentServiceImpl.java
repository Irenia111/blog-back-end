package com.irenia.blog.service;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.dao.CommentRepository;
import com.irenia.blog.prototype.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        return commentRepository.findByBlogIdAndParentCommentNull(
                blogId, Sort.by(Sort.Direction.ASC, "createTime"));
    }

    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new NotFoundException("parent comment not found")));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        comment.setAvatar(getAvatar());
        return commentRepository.save(comment);
    }

    private String getAvatar() {
        return "https://avatars.dicebear.com/api/gridy/"
                + new Random().nextInt(20000)
                + ".svg";
    }
}
