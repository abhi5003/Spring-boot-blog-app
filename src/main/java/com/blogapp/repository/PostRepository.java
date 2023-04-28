package com.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
