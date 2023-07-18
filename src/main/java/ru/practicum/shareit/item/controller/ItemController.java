package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Header;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAll(@RequestHeader(Header.X_SHARER_USER_ID) Long userId) {
        log.info("Получен запрос на получение всех вещей пользователя id-{}", userId);
        return new ResponseEntity<>(itemService.getAll(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getById(@PathVariable Long id) {
        log.info("Получен запрос на получение вещи id-{}", id);
        return new ResponseEntity<>(itemService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDto> create(@RequestHeader(Header.X_SHARER_USER_ID) Long userId,
                                          @Valid @RequestBody ItemDto itemDto) {
        log.info("Получен запрос на создание вещи пользователя id-{}", userId);
        return new ResponseEntity<>(itemService.create(itemDto, userId), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> update(@RequestBody ItemDto itemDto,
                          @PathVariable Long id,
                          @RequestHeader(Header.X_SHARER_USER_ID) Long userId) {
        log.info("Получен запрос на обновление вещи id-{} пользователя id-{}", id, userId);
        return new ResponseEntity<>(itemService.update(itemDto, id, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Получен запрос на удаление вещи id-{}", id);
        itemService.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestParam String text) {
        log.info("Получен запрос на поиск по тексту-{}", text);
        return new ResponseEntity<>(itemService.search(text), HttpStatus.OK);
    }
}
