package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.dto.GetItemDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static ru.practicum.shareit.Header.HEADER_USER;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestBody @Valid ItemDto dto, @RequestHeader(HEADER_USER) long ownerId) {
        log.info("POST /items");
        return itemService.addItem(dto,ownerId);
    }

    @PatchMapping(value = "/{itemId}")
    public ItemDto patchItem(@RequestBody ItemDto dto, @PathVariable long itemId,
                             @RequestHeader(HEADER_USER) long ownerId)  {
        log.info(String.format("PATCH /items/%s", itemId));
        return itemService.patchItem(dto,ownerId,itemId);
    }

    @GetMapping(value = "/{itemId}")
    public GetItemDto getItem(@PathVariable long itemId, @RequestHeader(HEADER_USER) long ownerId) {
        log.info(String.format("GET /items/%s", itemId));
        return itemService.getItem(itemId,ownerId);
    }

    @GetMapping
    public List<GetItemDto> getAllItemsByOwner(@RequestHeader(HEADER_USER) long ownerId,
                                               @RequestParam(required = false, defaultValue = "0")
                                               @Min(0)Integer from,
                                               @RequestParam(required = false, defaultValue = "10")
                                               @Min(0)Integer size) {
        log.info("GET /items");
        return itemService.getAllItemsByOwner(ownerId, from, size);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam String text, @RequestHeader(HEADER_USER) long ownerId,
                                    @RequestParam(required = false, defaultValue = "0")
                                    @Min(0) Integer from,
                                    @RequestParam(required = false, defaultValue = "10")
                                    Integer size) {
        log.info(String.format("GET /items/search?text=%s", text));
        return itemService.searchItem(text.toLowerCase(), ownerId, from, size);
    }

    @PostMapping("{itemId}/comment")
    public Comment addComment(@RequestBody @Valid Comment dto, @PathVariable long itemId,
                              @RequestHeader(HEADER_USER) long authorId) {
        log.info("POST /items/" + itemId + "/comment");
        return itemService.addComment(dto, itemId, authorId);
    }
}
