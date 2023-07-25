package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.impl.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.mapper.UserMapper.toUserDto;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public List<UserDto> getAll() {
        return userRepository.getAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id: " + id));

        return toUserDto(user);
    }

    @Override
    public UserDto create(User user) {
        return toUserDto(userRepository.create(user));
    }

    @Override
    public UserDto update(User user, Long id) {
        User updatedUser = userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден "));
        if (user.getEmail() != null && !user.getEmail().equals(updatedUser.getEmail())) {
            userRepository.checkingUniquenessEmail(user);
            userRepository.deleteEmail(id);
            updatedUser.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            updatedUser.setName(user.getName());
        }

        return toUserDto(userRepository.update(updatedUser));
    }

    @Override
    public void delete(Long id) {
        getById(id);
        userRepository.delete(id);
    }
}
