package com.blogapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;
import com.blogapp.service.PostService;
import com.blogapp.uttils.AppConstant;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
		return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@GetMapping
	public PostResponse getAllPost(
			@RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
			
			) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
		return new ResponseEntity<PostDto>(postService.getPostById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
		return new ResponseEntity<PostDto>(postService.updatePost(postDto, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id) {
		postService.deletePostById(id);
		return new ResponseEntity<>("Post entity deleted succesfully", HttpStatus.OK);
	}
}
