package com.ccamposhe.workhub.services;

import com.ccamposhe.workhub.domain.User;
import com.ccamposhe.workhub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User createUser(User user){
        if (repository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Este email ja esta cadastrado");
        }
        return repository.save(user);
    }

    public List<User> findAllUsers(){
        return repository.findAll();
    }

}
