package com.hosnaml.store.services;

import com.hosnaml.store.dtos.ChangePasswordRequest;
import com.hosnaml.store.dtos.RegisterUserRequest;
import com.hosnaml.store.dtos.UpdateUserRequest;
import com.hosnaml.store.entities.User;
import com.hosnaml.store.mappers.UserMapper;
import com.hosnaml.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> listUsers(String sort) {
        if (sort == null || !Set.of("name", "email").contains(sort)) {
            sort = "name";
        }
        return userRepository.findAll(Sort.by(sort));
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(RegisterUserRequest request) {
        User user = userMapper.toEntity(request);
        return userRepository.save(user);
    }

    public User updateUser(Long id, UpdateUserRequest request) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) return null;
        userMapper.update(request, existing);
        return userRepository.save(existing);
    }

    public boolean deleteUser(Long id) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) return false;
        userRepository.delete(existing);
        return true;
    }

    public User changePassword(Long id, ChangePasswordRequest request) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new SecurityException("Invalid old password");
        }
        user.setPassword(request.getNewPassword());
        return userRepository.save(user);
    }
}
