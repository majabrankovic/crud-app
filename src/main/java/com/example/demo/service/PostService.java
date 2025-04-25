package com.example.demo.service;

import com.example.demo.dao.PostRepository;
import com.example.demo.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

        return updateOldPost(post);
    }

    private Post updateOldPost(Post newPostData) {
        Optional<Post> optionalPost = postRepository.findById(newPostData.getId());
        if (optionalPost.isPresent()) {
            Post oldPost = optionalPost.get();
            oldPost.setTitle(newPostData.getTitle());
            oldPost.setContent(newPostData.getContent());
            oldPost.setUser(newPostData.getUser());
            return postRepository.save(oldPost);
        }else {
            return new Post();
        }
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }


}
