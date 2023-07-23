package ru.practicum.shareit.user.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> usersEmails = new TreeSet<>();
    private Long id = 1L;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> getById(Long id) {
        return users.get(id) == null ? Optional.empty() : Optional.of(users.get(id));
    }

    @Override
    public User create(User user) {
         if (checkingUniquenessEmail(user)) {
             usersEmails.add(user.getEmail());
         }
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        log.info("Пользователь {} добавлен ", user.toString());
        return user;
    }

    @Override
    public User update(User user) {
        usersEmails.add(user.getEmail());
        users.put(user.getId(), user);
        log.info("Пользователь {} обновлен ", user.toString());
        return user;
    }

    @Override
    public void delete(Long id) {
        deleteEmail(id);
        log.info("Пользователь {} удален ", id);
        users.remove(id);
    }

    @Override
    public void deleteEmail(Long id) {
        usersEmails.remove(users.get(id).getEmail());
    }

    @Override
    public boolean checkingUniquenessEmail(User user){
        if (usersEmails.contains(user.getEmail())) {
            throw new ConflictException("Пользователь с таким email уже зарегистрирован");
        }
        return true;
    }
}
