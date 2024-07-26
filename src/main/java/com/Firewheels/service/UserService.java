package com.Firewheels.service;

import com.Firewheels.model.User;
import com.Firewheels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findFirstByEmailAndPassword(email, password);
        return user != null;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById((long) id).orElse(null);
    }

    public void saveAllUser(List<User> users) {
        userRepository.saveAll(users);
    }
}
