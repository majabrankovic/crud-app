package com.example.demo.service;

import com.example.demo.dao.PostRepository;
import com.example.demo.entity.Post;
import com.example.demo.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post;

    @BeforeEach
    public void setUp() {
        post = Post.builder()
                .id(1L)
                .title("Naslov")
                .content("Sadrzaj posta")
                .build();

    }

    @Test
    public void PostService_CreatePost_ReturnPost() {
        when(postRepository.save(post)).thenReturn(post);

        Post result = postService.createPost(post);

        Assertions.assertThat(result).isEqualTo(post);
        verify(postRepository).save(post);
    }

    @Test
    public void PostService_GetPostById_ReturnPostWhenPostExists() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(1L);

        Assertions.assertThat(result).isEqualTo(post);
    }

    @Test
    public void PostService_GetPostById_ReturnEmptyPostWhenPostDoesNotExist() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        Post result = postService.getPostById(99L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void PostService_GetAllPosts_ReturnPosts() {
        List<Post> postList = List.of(post);
        when(postRepository.findAll()).thenReturn(postList);

        List<Post> result = postService.getAllPosts();

        Assertions.assertThat(result).isEqualTo(postList);
    }

    @Test
    public void PostService_UpdatePost_ReturnPostWhenPostExists() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postService.updatePost(post);

        Assertions.assertThat(result.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    public void PostService_UpdatePost_ReturnEmptyPostWhenPostDoesNotExist() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        Post result = postService.updatePost(post);

        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void PostService_DeletePostById_ReturnPostDeleted() {
        Long postId = 1L;

        postService.deletePostById(postId);

        verify(postRepository, times(1)).deleteById(postId);
    }

}
