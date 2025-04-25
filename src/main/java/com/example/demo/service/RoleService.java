package com.example.demo.service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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

             Role updated = oldRole.toBuilder()
            .name(newRoleData.getName())
            .userRoles(newRoleData.getUserRoles()).build();

            return roleRepository.save(updated);
        }else {
            return Role.builder().build();
        }
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

}
