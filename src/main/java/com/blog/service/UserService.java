package com.blog.service;

import com.blog.dto.UserDto;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.UserMapper;
import com.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Set<String> connectedUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());


    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id)));
    }

    public UserDto createUser(User user) {
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPassword(userDetails.getPassword());
            return userMapper.toDto(userRepository.save(existingUser));
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

    public UserDto loadByemail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email :", email)));
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
