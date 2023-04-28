package com.blogapp.controller;

import com.blogapp.payload.CommentDto;
import com.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable(value = "postId") long postId){
        return commentService.getAllCommentsByPostID(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId){
        return new ResponseEntity<CommentDto>(commentService.getCommentById(postId,commentId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "commentId") long commentId,
                                                    @RequestBody CommentDto commentDto){
        return new ResponseEntity<CommentDto>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);

    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "postId") long postId,
                                             @PathVariable(value = "commentId") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted succesfully", HttpStatus.OK);
    }
}
