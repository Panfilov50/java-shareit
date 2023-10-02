package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.RequestUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

import static ru.practicum.shareit.Header.SHAREIT_SERVER;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    public UserClient(@Value(SHAREIT_SERVER) String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> add(RequestUserDto dto) {
        return post(" ", dto);
    }

    public ResponseEntity<Object> update(long userId, UpdateUserDto dto) {
        return patch("/" + userId, dto);
    }

    public ResponseEntity<Object> remove(long userId) {
        return delete("/" + userId);
    }

    public ResponseEntity<Object> getAll() {
        return get("/");
    }

    public ResponseEntity<Object> getByUserId(long userId) {
        return get("/" + userId);
    }
}