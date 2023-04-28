package com.blogapp.service;

import com.blogapp.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long id, CommentDto commentDto);
    List<CommentDto> getAllCommentsByPostID(long postID);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto commentRequest);
    void deleteComment(long postId, long commentId);
}
