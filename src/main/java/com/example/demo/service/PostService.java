package com.example.demo.service;

import com.example.demo.dao.PostRepository;
import com.example.demo.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(new Post());
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Post post) {
        Post oldPost = null;
        Optional<Post> optionalPost = postRepository.findById(post.getId());
        if (optionalPost.isPresent()) {
            oldPost = optionalPost.get();
            oldPost.setTitle(post.getTitle());
            oldPost.setContent(post.getContent());
            postRepository.save(oldPost);
        }else {
            return new Post();
        }

        return oldPost;
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }


}
