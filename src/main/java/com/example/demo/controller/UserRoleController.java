package com.example.demo.controller;

import com.example.demo.entity.UserRole;
import com.example.demo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-role")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("/add-user-role")
    public UserRole addUserRole(@RequestBody UserRole userRole) {
        return userRoleService.createUserRole(userRole);
    }

    @GetMapping("/{id}")
    public UserRole getUserRoleById(@PathVariable Long id) {
        return userRoleService.getUserRoleByUserId(id);
    }

    @GetMapping("/user-roles")
    public List<UserRole> getAllUserRole() {
        return userRoleService.getAllUserRoles();
    }

    @PutMapping("/update-user-role")
    public UserRole updateUserRole(@RequestBody UserRole userRole) {
        return userRoleService.updateUserRole(userRole);
    }

    @DeleteMapping("/{id}")
    public void deleteUserRoleById(@PathVariable Long id) {
        userRoleService.deleteUserRoleById(id);
    }

}
