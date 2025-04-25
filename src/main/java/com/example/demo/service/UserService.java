package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> createUsers(List<User> users) {
        return userRepository.saveAll(users);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {

        return updateOldUser(user);

    }

    private User updateOldUser(User newUserData) {
        Optional<User> optionalUser = userRepository.findById(newUserData.getId());
        if(optionalUser.isPresent()) {
            User oldUser = optionalUser.get();

            User updated = oldUser.toBuilder()
                    .name(newUserData.getName())
                    .address(newUserData.getAddress())
                    .profile(newUserData.getProfile())
                    .userRoles(newUserData.getUserRoles())
                    .posts(newUserData.getPosts()).build();

            return userRepository.save(updated);
        }else {
            return User.builder().build();
        }
    }

    public String deleteUserById(Long id) {
        userRepository.deleteById(id);
        return "User deleted";
    }


}
