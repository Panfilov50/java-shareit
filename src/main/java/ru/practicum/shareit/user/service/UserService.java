package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;



import java.util.List;

public interface UserService {

    UserDto add(UserDto userDto);

    UserDto getUserById(long id);

    List<UserDto> getAllUsers();

    UserDto update(UserDto userDto, long id);

    void delete(long id);

    boolean existsById(Long id);
}
