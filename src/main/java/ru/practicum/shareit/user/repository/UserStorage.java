package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserStorage {
    User add(User user);

    User get(long id);

    Collection<User> getAll();

    User update(User user, long id);

    void delete(long id);
}