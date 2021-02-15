package ru.list.surkovr.gymservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.list.surkovr.gymservice.converters.DtoConverter;
import ru.list.surkovr.gymservice.domain.User;
import ru.list.surkovr.gymservice.dto.UserDto;
import ru.list.surkovr.gymservice.services.interfaces.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final DtoConverter dtoConverter;

    public UserController(UserService userService, DtoConverter dtoConverter) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping
    private ResponseEntity<List<UserDto>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(dtoConverter.convertUsers(users));
    }

    @GetMapping("{id}")
    private ResponseEntity<UserDto> getOne(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(dtoConverter.convert(user));
    }

    @PutMapping("{id}")
    private ResponseEntity<UserDto> edit(@RequestBody UserDto userDto, @PathVariable Long id) {
        User user = userService.edit(id, userDto.getLastName(), userDto.getFirstName(), userDto.getMiddleName(),
                userDto.getUsername());
        return ResponseEntity.ok(dtoConverter.convert(user));
    }

    @PostMapping
    private ResponseEntity<UserDto> add(@RequestBody UserDto userDto) {
        User user = userService.add(userDto.getLastName(), userDto.getFirstName(), userDto.getMiddleName(),
                userDto.getUsername());
        return ResponseEntity.ok(dtoConverter.convert(user));
    }
}
