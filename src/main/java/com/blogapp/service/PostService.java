package com.blogapp.service;

import java.util.List;

import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(Long id);
	
	PostDto updatePost(PostDto postDto, Long id);
	
	void deletePostById(Long id);

}
