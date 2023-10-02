package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestAddDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.Header.HEADER_USER;

@Slf4j
@Validated
@Controller
@AllArgsConstructor
@RequestMapping("/requests")
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(name = HEADER_USER) Long userId,
                                      @Valid @RequestBody RequestAddDto dto) {
        log.info("POST /requests");
        return requestClient.add(userId, dto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllRequestByOwnerId(@RequestHeader(name = HEADER_USER) Long userId) {
        log.info("GET /requests");
        return requestClient.getAllRequestByOwnerId(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequest(@RequestHeader(name = HEADER_USER) Long userId,
                                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                @Positive @RequestParam(defaultValue = "20") Integer size) {
        log.info("GET /requests/all?from=" + from + "&size=" + size);
        return requestClient.getAllRequest(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getByRequestIdWithItem(@PathVariable Long requestId, @RequestHeader(name = HEADER_USER) Long userId) {
        log.info("GET /requests/" + requestId);
        return requestClient.getByRequestIdWithItem(requestId, userId);
    }
}