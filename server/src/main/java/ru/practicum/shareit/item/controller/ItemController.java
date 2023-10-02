package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.core.mapper.PaginationMapper;

import ru.practicum.shareit.item.dto.AddCommentDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemGetDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

import static ru.practicum.shareit.Header.HEADER_USER;

@Slf4j
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;

    @PostMapping
    public ItemDto add(@RequestHeader(name = HEADER_USER) long userId, @RequestBody ItemDto itemDto) {
        log.info("POST /items");
        return itemMapper.itemToItemDto(itemService.add(userId, itemDto.getRequestId(), itemMapper.itemDtoToItem(itemDto)));
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@PathVariable long itemId,
                          @RequestHeader(name = HEADER_USER) long userId,
                          @RequestBody UpdateItemDto itemDto) {
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
        log.info("GET /items");
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
    public CommentDto comment(@PathVariable long itemId,
                              @RequestHeader(name = HEADER_USER) long userId,
                              @RequestBody AddCommentDto addCommentDto) {
        log.info("POST /items/" + itemId + "/comment");
        return commentMapper.commentToCommentDto(
                itemService.addComment(itemId, userId, commentMapper.addCommentDtoToComment(addCommentDto)));
    }
}
