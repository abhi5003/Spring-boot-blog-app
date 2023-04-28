package com.blogapp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.entity.Post;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto) {

		// convert Dto to entity
		Post post = mapEntityToDto(postDto);
		Post newPost = postRepository.save(post);

		// convert entity to Dto
		PostDto postResponce = mapToDto(newPost);

		return postResponce;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() 
				: Sort.by(sortBy).descending();
		
		
		org.springframework.data.domain.Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> allPost = postRepository.findAll(pageable);
		List<Post> listsOfPage=allPost.getContent();
		List<PostDto> content= listsOfPage.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(allPost.getNumber());
		postResponse.setPageSize(allPost.getSize());
		postResponse.setTotalElements(allPost.getTotalElements());
		postResponse.setTotalPages(allPost.getTotalPages());
		postResponse.setLast(allPost.isLast());
		
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		Post updatedPost = postRepository.save(post);

		return mapToDto(updatedPost);
	}

	@Override
	public void deletePostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		postRepository.delete(post);
	}

	private PostDto mapToDto(Post post) {
		PostDto postDto = new PostDto();

		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());

		return postDto;
	}

	private Post mapEntityToDto(PostDto postDto) {
		Post post = new Post();

		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		return post;
	}

}
