package com.example.reddit.post.repository;

import com.example.reddit.post.Post;
import com.example.reddit.user.User;
import com.example.reddit.user.repository.custom.UserMapperRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {}
