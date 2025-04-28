package com.example.demo.service;

import com.example.demo.dao.UserRoleRepository;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    @Mock
    UserRoleRepository userRoleRepository;

    @InjectMocks
    UserRoleService userRoleService;

    private UserRole userRole;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1L)
                .name("Test User")
                .build();

        Role role = Role.builder()
                .id(1L)
                .name("ADMIN")
                .build();
        userRole = UserRole.builder()
                .id(1L)
                .user(user)
                .role(role)
                .active(true)
                .assignedAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void UserRoleService_CreateUserRole_ReturnUserRole() {
        when(userRoleRepository.save(userRole)).thenReturn(userRole);

        UserRole result = userRoleService.createUserRole(userRole);

        Assertions.assertThat(result).isEqualTo(userRole);
        verify(userRoleRepository).save(userRole);
    }

    @Test
    public void UserRoleService_GetUserRoleById_ReturnUserRoleWhenUserRoleExists() {
        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(userRole));

        UserRole result = userRoleService.getUserRoleByUserId(1L);

        Assertions.assertThat(result).isEqualTo(userRole);
    }

    @Test
    public void UserRoleService_GetUserRoleById_ReturnEmptyUserRoleWhenUserRoleDoesNotExist() {
        when(userRoleRepository.findById(99L)).thenReturn(Optional.empty());

        UserRole result = userRoleService.getUserRoleByUserId(99L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void UserRoleService_GetAllUserRoles_ReturnUserRoles() {
        List<UserRole> userRoleList = List.of(userRole);
        when(userRoleRepository.findAll()).thenReturn(userRoleList);

        List<UserRole> result = userRoleService.getAllUserRoles();

        Assertions.assertThat(result).isEqualTo(userRoleList);
    }

    @Test
    public void UserRoleService_UpdateUserRole_ReturnUserRoleWhenUserRoleExists() {
        when(userRoleRepository.findById(userRole.getId())).thenReturn(Optional.of(userRole));
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(userRole);

        UserRole result = userRoleService.updateUserRole(userRole);

        Assertions.assertThat(result.getUser().getId()).isEqualTo(userRole.getUser().getId());
        Assertions.assertThat(result.getRole().getId()).isEqualTo(userRole.getRole().getId());
        Assertions.assertThat(result.isActive()).isEqualTo(userRole.isActive());
    }

    @Test
    public void UserRoleService_UpdateUserRole_ReturnEmptyUserRoleWhenUserRoleDoesNotExist() {
        when(userRoleRepository.findById(userRole.getId())).thenReturn(Optional.empty());

        UserRole result = userRoleService.updateUserRole(userRole);

        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void UserRoleService_DeleteUserRoleById_ReturnUserRoleDeleted() {
        Long userRoleId = 1L;

        userRoleService.deleteUserRoleById(userRoleId);

        verify(userRoleRepository, times(1)).deleteById(userRoleId);
    }

}
