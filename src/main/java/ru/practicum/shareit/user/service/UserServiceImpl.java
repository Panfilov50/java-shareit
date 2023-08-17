package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto add(UserDto userDto) {
        log.info("Добавлен новый пользователь");
        User user = UserMapper.mapToUser(userDto);
        return UserMapper.mapToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserById(long id) {

        if (userRepository.findById(id).isPresent()) {
            log.info("Получен пользователь с id " + id);
            return UserMapper.mapToUserDto(userRepository.findById(id).get());
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(UserDto userDto, long id) {
        User user = UserMapper.mapToUser(userDto);
        user.setId(id);
        if (user.getName() == null) {
            if (userRepository.findById(id).isPresent()) {
                user.setName(userRepository.findById(id).get().getName());
            } else {
                throw new NotFoundException();
            }
        }
        if (user.getEmail() == null) {
            if (userRepository.findById(id).isPresent()) {
                user.setEmail(userRepository.findById(id).get().getEmail());
            } else {
                throw new NotFoundException();
            }
        }
        log.info("Обновлен пользователь с id " + id);
        return UserMapper.mapToUserDto(userRepository.save(user));
    }

    @Override
    public void delete(long id) {
        log.info("Удалён пользователь с id " + id);
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {

        return userRepository.existsById(id);
    }
}
