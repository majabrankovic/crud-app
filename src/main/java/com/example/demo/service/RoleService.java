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
        Role oldRole = null;
        Optional<Role> optionalRole = roleRepository.findById(role.getId());
        if (optionalRole.isPresent()) {
            oldRole = optionalRole.get();
            oldRole.setName(role.getName());
            roleRepository.save(oldRole);
        }else {
            return new Role();
        }
        return oldRole;
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

}
