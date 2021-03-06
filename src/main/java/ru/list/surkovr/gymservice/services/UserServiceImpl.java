package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.list.surkovr.gymservice.domain.User;
import ru.list.surkovr.gymservice.repositories.UserRepository;
import ru.list.surkovr.gymservice.services.interfaces.UserService;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User edit(Long id, String lastName, String firstName, String middleName, String username) {
        User currentUser = userRepository.getOne(id);
        currentUser.setLastName(lastName);
        currentUser.setFirstName(firstName);
        currentUser.setMiddleName(middleName);
        currentUser.setUsername(username);
        currentUser = userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    public User add(String lastName, String firstName, String middleName, String username, String password) {
        User user;
        if (StringUtils.hasText(lastName) && StringUtils.hasText(firstName)) {
            User newUser = User.builder().lastName(lastName).firstName(firstName)
                    .middleName(middleName).username(username).password(password).build();
            user = userRepository.save(newUser);
        } else {
            user = null;
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
