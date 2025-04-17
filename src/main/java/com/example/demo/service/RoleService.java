package com.example.demo.service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(new Role());
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role updateRole(Role role) {

        return updateOldRole(role);

    }

    private Role updateOldRole(Role newRoleData) {
        Optional<Role> optionalRole = roleRepository.findById(newRoleData.getId());
        if (optionalRole.isPresent()) {
            Role oldRole = optionalRole.get();
            oldRole.setName(newRoleData.getName());
            oldRole.setUsers(newRoleData.getUsers());
            return roleRepository.save(oldRole);
        }else {
            return null;
        }
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

}
