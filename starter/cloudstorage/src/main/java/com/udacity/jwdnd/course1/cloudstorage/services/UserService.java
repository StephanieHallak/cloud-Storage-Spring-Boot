package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private HashService hashService;

    public UserService(UserRepository userRepository, HashService hashService) {
        this.userRepository = userRepository;
        this.hashService = hashService;
    }
//THIS METHOD RETURN TRUE IS USERNAME ALREADY EXISTS - FALSE IF USERNAME DOESN'T EXISTS
    public Boolean usernameExists(String username){
        if (userRepository.findByUsername(username) == null){
            return false;
        }
        return true;
    }

    public User createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        User createdUser = new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName());
        return userRepository.save(createdUser);
    }

        public User getUser(String username){
            return userRepository.findByUsername(username);
        }

}
