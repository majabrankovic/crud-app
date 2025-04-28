package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .name("Maja")
                .address("Zemun")
                .build();
    }

    @Test
    public void UserService_CreateUser_ReturnUser() {
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        Assertions.assertThat(result).isEqualTo(user);
        verify(userRepository).save(user);
    }

    @Test
    public void UserService_CreateUsers_ReturnUsers() {
        List<User> userList = List.of(user);
        when(userRepository.saveAll(userList)).thenReturn(userList);

        List<User> result = userService.createUsers(userList);

        Assertions.assertThat(result).isEqualTo(userList);
        verify(userRepository).saveAll(userList);
    }

    @Test
    public void UserService_GetUserById_ReturnUserWhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        Assertions.assertThat(result).isEqualTo(user);
    }

    @Test
    public void UserService_GetUserById_ReturnEmptyUserWhenUserDoesNotExist() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        User result = userService.getUserById(99L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void UserService_GetUsers_ReturnUsers() {
        List<User> userList = List.of(user);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getUsers();

        Assertions.assertThat(result).isEqualTo(userList);
    }

    @Test
    public void UserService_UpdateUser_ReturnUserWhenUserExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(user);

        Assertions.assertThat(result.getName()).isEqualTo(user.getName());
    }

    @Test
    public void UserService_UpdateUser_ReturnEmptyUserWhenUserDoesNotExist() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        User result = userService.updateUser(user);

        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void UserService_DeleteUser_ReturnUserDeleted() {
        doNothing().when(userRepository).deleteById(1L);

        String result = userService.deleteUserById(1L);

        Assertions.assertThat(result).isEqualTo("User deleted");
        verify(userRepository).deleteById(1L);
    }

}
