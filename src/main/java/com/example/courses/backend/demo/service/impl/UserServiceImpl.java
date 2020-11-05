package com.example.courses.backend.demo.service.impl;

import com.example.courses.backend.demo.model.User;
import com.example.courses.backend.demo.model.exceptions.UserAlreadyExistsException;
import com.example.courses.backend.demo.model.exceptions.UserNotFoundException;
import com.example.courses.backend.demo.repository.UserRepository;
import com.example.courses.backend.demo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String username) {
        return this.userRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteById(String username) {
        this.userRepository.deleteById(username);
    }

    @Override
    public User registerUser(User user) {
        if(this.userRepository.existsById(user.getUsername())){
            throw new UserAlreadyExistsException(user.getUsername());
        }
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findById(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
