package com.blogapp.service.impl;

import com.blogapp.entity.Comment;
import com.blogapp.entity.Post;
import com.blogapp.exception.BlogAppException;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.payload.CommentDto;
import com.blogapp.repository.CommentRepository;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment=mapToEntity(commentDto);

        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
        comment.setPost(post);
        Comment newComment =commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostID(long postID) {
        List<CommentDto> allComments=new ArrayList<>();
        List<Comment> getAllComments=commentRepository.findByPostId(postID);
        allComments=getAllComments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

        return allComments;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "commentId", commentId));
        if (comment.getPost().getId().equals(post.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "commentId", commentId));
        if (comment.getPost().getId().equals(post.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updateComment=commentRepository.save(comment);

        return mapToDto(updateComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "commentId", commentId));
        if (comment.getPost().getId().equals(post.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }
}
