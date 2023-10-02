package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.RequestUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDto add(@Valid @RequestBody RequestUserDto userDto) {
        log.info("POST /users");
        //Обычно mapper используется на уровне контроллеров
        return userMapper.userToUserDto(userService.add(userMapper.requestUserDtoToUser(userDto)));
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id, @Valid @RequestBody UpdateUserDto userDto) {
        log.info(String.format("PATCH /users/%s", id));
        return userMapper.userToUserDto(userService.update(id, userMapper.updateUserDtoToUser(userDto)));
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        log.info(String.format("DELETE /users/%s", id));
        userService.remove(id);
    }

    @GetMapping
    public List<UserDto> getAll() {
        log.info("GET /users");
        return userMapper.userListToUserDtoList(userService.getAll());
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        log.info(String.format("GET /users/%s", id));
        return userMapper.userToUserDto(userService.getByUserId(id));
    }
}