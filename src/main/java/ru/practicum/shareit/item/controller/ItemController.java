package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.ItemDtoWithCommments;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.Header.HEADER_USER;

@Slf4j
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody @Valid ItemDto dto,
                                           @RequestHeader(HEADER_USER) long ownerId) {
        log.info("Получен запрос на добавление вещи от пользователя id-{}", ownerId);
        return new ResponseEntity<>(itemService.addItem(dto,ownerId), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto dto,
                                              @PathVariable long itemId,
                                              @RequestHeader(HEADER_USER) long ownerId) {
        log.info("Получен запрос на обновление вещи id-{} от пользователя id-{}", itemId, ownerId);
        return new ResponseEntity<>(itemService.updateItem(dto,ownerId,itemId), HttpStatus.OK);
    }

    @GetMapping(value = "/{itemId}")
    public ResponseEntity<ItemDtoWithCommments> getItem(@PathVariable long itemId,
                                                        @RequestHeader(HEADER_USER) long ownerId) {
        log.info("Получен запрос на получения вещи id-{} от пользователя id-{}", itemId, ownerId);
        return new ResponseEntity<>(itemService.getItem(itemId,ownerId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemDtoWithCommments>> getAllItemsByOwner(@RequestHeader(HEADER_USER) long ownerId) {
        log.info("Получен запрос на получения списка всех вещей пользователя id-{}", ownerId);
        return new ResponseEntity<>(itemService.getAllItemsByOwner(ownerId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItem(@RequestParam String text,
                                                    @RequestHeader(HEADER_USER) long ownerId) {
        log.info("Получен запрос на поиск -{}", text);
        return new ResponseEntity<>(itemService.searchItem(text.toLowerCase(),ownerId), HttpStatus.OK);
    }

    @PostMapping("{itemId}/comment")
    public ResponseEntity<Comment> addComment(@RequestBody @Valid Comment dto,
                                              @PathVariable long itemId,
                                              @RequestHeader(HEADER_USER) long authorId) {

        log.info("Получен запрос на добавление комментария от пользователя id-{} ", authorId);
        return new ResponseEntity<>(itemService.addComment(dto, itemId, authorId), HttpStatus.OK);
    }
}

