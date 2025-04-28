package com.example.demo.service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.entity.Profile;
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
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(1L)
                .name("ADMIN")
                .build();
    }

    @Test
    public void RoleService_CreateRole_ReturnRole() {
        when(roleRepository.save(role)).thenReturn(role);

        Role result = roleService.createRole(role);

        Assertions.assertThat(result).isEqualTo(role);
        verify(roleRepository).save(role);
    }

    @Test
    public void RoleService_GetRoleById_ReturnRoleWhenRoleExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Role result = roleService.getRoleById(1L);

        Assertions.assertThat(result).isEqualTo(role);
    }

    @Test
    public void RoleService_GetRoleById_ReturnEmptyRoleWhenRoleDoesNotExist() {
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        Role result = roleService.getRoleById(99L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void RoleService_GetAllRoles_ReturnRoles() {
        List<Role> roleList = List.of(role);
        when(roleRepository.findAll()).thenReturn(roleList);

        List<Role> result = roleService.getAllRoles();

        Assertions.assertThat(result).isEqualTo(roleList);
    }

    @Test
    public void RoleService_UpdateRole_ReturnRoleWhenRoleExists() {
        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role result = roleService.updateRole(role);

        Assertions.assertThat(result.getName()).isEqualTo(role.getName());
    }

    @Test
    public void RoleService_UpdateRole_ReturnEmptyRoleWhenRoleDoesNotExist() {
        when(roleRepository.findById(role.getId())).thenReturn(Optional.empty());

        Role result = roleService.updateRole(role);

        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void RoleService_DeleteRoleById_ReturnRoleDeleted() {
        Long roleId = 1L;

        roleService.deleteRoleById(roleId);

        verify(roleRepository, times(1)).deleteById(roleId);
    }

}
