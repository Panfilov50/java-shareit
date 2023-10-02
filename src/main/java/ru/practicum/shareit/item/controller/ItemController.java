package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.PaginationMapper;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.Header.HEADER_USER;


@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;

    @PostMapping
    public ItemDto add(@RequestHeader(name = HEADER_USER) long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("POST /items");
        return itemMapper.itemToItemDto(itemService.add(userId, itemDto.getRequestId(), itemMapper.itemDtoToItem(itemDto)));
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@PathVariable long itemId,
                          @RequestHeader(name = HEADER_USER) long userId,
                          @Valid @RequestBody UpdateItemDto itemDto) {
        log.info(String.format("PATCH /items/%s", itemId));
        return itemMapper.itemToItemDto(itemService.update(itemId, userId, itemMapper.updateItemDtoToItem(itemDto)));
    }

    @DeleteMapping("/{itemId}")
    public void remove(@PathVariable long itemId) {
        log.info(String.format("GET /items/%s", itemId));
        itemService.remove(itemId);
    }

    @GetMapping("/{itemId}")
    public ItemGetDto getByItemId(@PathVariable long itemId, @RequestHeader(name = HEADER_USER) long userId) {
        log.info("Запрос на выдачу предмета");
        return itemMapper.itemToItemGetDto(itemService.getByItemId(itemId, userId));
    }

    @GetMapping
    public List<ItemGetDto> getByOwnerId(@RequestHeader(name = HEADER_USER) long ownerId,
                                         @RequestParam(required = false) Integer from,
                                         @RequestParam(required = false) Integer size) {
        log.info("GET /items");
        return itemMapper.itemListToItemGetDtoList(itemService.getByOwnerId(ownerId, PaginationMapper.toMakePage(from, size)));
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam(required = false) String text,
                                @RequestParam(required = false) Integer from,
                                @RequestParam(required = false) Integer size) {
        log.info(String.format("GET /items/search?text=%s", text));
        return itemMapper.itemListToItemDtoList(itemService.search(text, PaginationMapper.toMakePage(from, size)));
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto comment(
            @PathVariable long itemId,
            @RequestHeader(name = HEADER_USER) long userId,
            @Valid @RequestBody AddCommentDto addCommentDto
    ) {
        log.info("POST /items/" + itemId + "/comment");
        return commentMapper.commentToCommentDto(
                itemService.addComment(itemId, userId, commentMapper.addCommentDtoToComment(addCommentDto)));
    }
}