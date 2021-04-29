package com.irenia.blog.service;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.dao.CommentRepository;
import com.irenia.blog.prototype.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //这里需要将回复的评论进行扁平化
        List<Comment> origComments = commentRepository.findByBlogIdAndParentCommentNull(
                blogId, Sort.by(Sort.Direction.ASC, "createTime"));
        return flat(origComments);
    }

    private List<Comment> flat(List<Comment> origComments) {
        //复制一份新的commentList
        List<Comment> comments = new ArrayList<>();
        for (Comment comment : origComments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            comments.add(c);
        }

        //采用递归，将comment扁平化
        combineChildren(comments);
        return comments;
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> childrenComments = comment.getReplyComments();
            for (Comment reply : childrenComments) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
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
