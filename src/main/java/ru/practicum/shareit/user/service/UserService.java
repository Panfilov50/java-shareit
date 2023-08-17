package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;



import java.util.List;

public interface UserService {

    public UserDto add(UserDto userDto);

    public UserDto getUserById(long id);

    public List<UserDto> getAllUsers();

    public UserDto update(UserDto userDto, long id);

    public void delete(long id);

    public boolean existsById(Long id);
}
