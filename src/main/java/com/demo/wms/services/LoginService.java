package com.demo.wms.services;

import com.demo.wms.domain.User;
import com.demo.wms.exeptions.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by toxa on 8/21/2016.
 */
@Service
public class LoginService {

    @Autowired
    private UserService userService;

    public User login(String login, String password) throws LoginException {
        User user = userService.findUserByLogin(login);
        if (user == null) {
            throw new LoginException("Unknown user");
        }
        if(!user.getPassword().equals(password)){
            throw new LoginException("Password doesn't match");
        }
        return user;

    }

}
