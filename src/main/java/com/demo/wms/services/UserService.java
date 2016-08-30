package com.demo.wms.services;

import com.demo.wms.domain.User;
import com.demo.wms.domain.UserRole;
import com.demo.wms.exeptions.LoginAlreadyExistsException;
import com.demo.wms.repository.RoleRepository;
import com.demo.wms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by toxa on 8/21/2016.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    public UserService() {
    }

    public User findUserByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    public User findUser(Long id) {
        return repository.findOne(id);
    }

    public List<User> getUsers() {
        return (List<User>) repository.findAll();
    }

    public User saveUser(User user) {
            return repository.save(user);
    }


    public void delete(Long id) {
        repository.delete(id);
    }


    public boolean isLoginExists(String login) {
        return findUserByLogin(login) != null;
    }
}
