package ru.list.surkovr.gymservice.services.interfaces;

import ru.list.surkovr.gymservice.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User edit(Long id, String lastName, String firstName, String middleName, String username);

    User add(String lastName, String firstName, String middleName, String username);
}
