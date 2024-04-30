package com.nttdata.ta.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class UserService implements ItemService<User> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, String> verificationCodes = new HashMap<>();

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public Boolean addItem(User user){
        try {
            userRepository.save(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @Override
    public Boolean deleteItem(User user){
        try {
            userRepository.delete(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @Override
    public Boolean updateItem(User user){
        try {
            userRepository.save(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @Override
    public List<User> getAllItem(){
        return userRepository.findAll();
    }
    @Override
    public User getItem(Long id){

        return userRepository.getById(id);
    }
    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public Boolean updateItemById(Long id, User updatedUser){
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                user.setUsername(updatedUser.getUsername());
                user.setPassword(updatedUser.getPassword());
                user.setEmail(updatedUser.getEmail());
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }
    public boolean registerUser(User userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists.");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return true;
    }

    public boolean loginUser(User userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        return true;
    }
    public Boolean verifyAccount(String email, String code) {
        String storedCode = verificationCodes.get(email);
        if (storedCode != null && code.equals(storedCode)) {
            verificationCodes.remove(email);
            User user = userRepository.findByEmail(email);
            if (user != null) {
                user.setVerified(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
    private String generateJwtToken(User user) {
        return "JWT token";
    }
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }
}

