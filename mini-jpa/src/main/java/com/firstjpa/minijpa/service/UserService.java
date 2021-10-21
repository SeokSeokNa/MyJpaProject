package com.firstjpa.minijpa.service;

import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long join(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
        return user.getId();
    }

    //중복체크
    public Long overlapId(String userId) {
        return userRepository.findByUserIdOverlap(userId);
    }

    public List<User> login(String userId, String password) {
        return userRepository.findByUserId(userId , password);
    }

}
