package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.AddCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;

import static ru.practicum.shareit.Header.HEADER_USER;

@Slf4j
@Validated
@Controller
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(name = HEADER_USER) Long userId,
                                      @Valid @RequestBody ItemDto dto) {
        log.info("POST /items");
        return itemClient.add(userId, dto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@PathVariable Long itemId, @RequestHeader(name = HEADER_USER) Long userId,
                                         @Valid @RequestBody UpdateItemDto dto) {
        log.info(String.format("PATCH /items/%s", itemId));
        return itemClient.update(itemId, userId, dto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> remove(@PathVariable Long itemId) {
        log.info(String.format("GET /items/%s", itemId));
        return itemClient.remove(itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getByItemId(@PathVariable Long itemId, @RequestHeader(name = HEADER_USER) Long userId) {
        log.info("Запрос на выдачу предмета");
        return itemClient.getByItemId(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getByOwnerId(@RequestHeader(name = HEADER_USER) Long userId,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                               @Positive @RequestParam(defaultValue = "20") Integer size) {
        log.info("GET /items");
        return itemClient.getByOwnerId(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String text,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "20") Integer size) {
        log.info(String.format("GET /items/search?text=%s", text));
        if (text.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return itemClient.search(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> comment(@PathVariable Long itemId,
                                          @RequestHeader(name = HEADER_USER) Long userId,
                                          @Valid @RequestBody AddCommentDto dto) {
        log.info("POST /items/" + itemId + "/comment");
        return itemClient.comment(itemId, userId, dto);
    }
}
