package com.fundamentosplatzi.springboot.fundamentos.service;

import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final Log LOG = LogFactory.getLog(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public void saveTransactional(List<User> users) {
        users.stream().peek(user -> LOG.info("User insert: " + user))
                .forEach(userRepository::save);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User postUser) {
        return userRepository.save(postUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(User newUser, Long id) {
        User userDB = userRepository.findById(id).orElseThrow();
        userDB.setName(newUser.getName());
        userDB.setEmail(newUser.getEmail());
        userDB.setBirthDate(newUser.getBirthDate());
        return userRepository.save(userDB);
    }
}
