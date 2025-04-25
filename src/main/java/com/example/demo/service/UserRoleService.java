package com.example.demo.service;

import com.example.demo.dao.UserRoleRepository;
import com.example.demo.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole createUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public UserRole getUserRoleByUserId(Long id) {
        return userRoleRepository.findById(id).orElse(new UserRole());
    }

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public UserRole updateUserRole(UserRole userRole) {
        return updateOldUserRole(userRole);
    }

    private UserRole updateOldUserRole(UserRole newUserRoleData) {

        Optional<UserRole> optionalUserRole = userRoleRepository.findById(newUserRoleData.getId());
        if (optionalUserRole.isPresent()) {
            UserRole oldUserRole = optionalUserRole.get();

            UserRole updated = oldUserRole.toBuilder()
            .user(newUserRoleData.getUser())
            .role(newUserRoleData.getRole())
            .active(newUserRoleData.isActive())
            .assignedAt(newUserRoleData.getAssignedAt()).build();

            return userRoleRepository.save(updated);
        }else {
            return UserRole.builder().build();
        }

    }

    public void deleteUserRoleById(Long id) {
        userRoleRepository.deleteById(id);
    }

}
