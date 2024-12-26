package com.blog.service;

import com.blog.entity.User;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Set<String> connectedUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPassword(userDetails.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public void addUser(String username) {
        connectedUsers.add(username);
    }
    public Set<String> getConnectedUsers() {
        return new HashSet<>(connectedUsers);
    }

    public void removeUser(String username) {
        connectedUsers.remove(username);
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    user.setStatus("OFFLINE");
                    userRepository.save(user);
                });
    }
}
