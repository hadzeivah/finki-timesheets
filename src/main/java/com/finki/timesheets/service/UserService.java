package com.finki.timesheets.service;

import com.finki.timesheets.model.User;
import com.finki.timesheets.model.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
}
