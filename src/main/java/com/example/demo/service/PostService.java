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

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

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

             Post updated = oldPost.toBuilder()
            .title(newPostData.getTitle())
            .content(newPostData.getContent())
            .user(newPostData.getUser()).build();

            return postRepository.save(updated);
        }else {
            return Post.builder().build();
        }
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }


}
