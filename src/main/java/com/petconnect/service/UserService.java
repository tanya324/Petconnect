package com.petconnect.service;

import com.petconnect.model.User;
import com.petconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Email already registered: " + user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) { return userRepository.findByEmail(email); }
    public Optional<User> findById(Long id)         { return userRepository.findById(id); }
    public List<User>     getAllUsers()              { return userRepository.findAll(); }
    public long           countUsers()              { return userRepository.count(); }

    public User updateUser(Long id, User updated) {
        User u = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
        u.setName(updated.getName());
        u.setPhone(updated.getPhone());
        u.setAddress(updated.getAddress());
        return userRepository.save(u);
    }

    public void deleteUser(Long id) { userRepository.deleteById(id); }
}
