package com.example.demo.service;

import com.example.demo.dao.UserRoleRepository;
import com.example.demo.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

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
            oldUserRole.setUser(newUserRoleData.getUser());
            oldUserRole.setRole(newUserRoleData.getRole());
            oldUserRole.setActive(newUserRoleData.isActive());
            oldUserRole.setAssignedAt(newUserRoleData.getAssignedAt());
            return userRoleRepository.save(oldUserRole);
        }else {
            return null;
        }

    }

    public void deleteUserRoleById(Long id) {
        userRoleRepository.deleteById(id);
    }

}
