package ru.practicum.shareit.request.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.PaginationMapper;
import ru.practicum.shareit.request.dto.RequestAddDto;
import ru.practicum.shareit.request.dto.RequestAnswerDto;
import ru.practicum.shareit.request.dto.RequestWithItemDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.service.RequestService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.Header.HEADER_USER;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class RequestController {
    private final RequestMapper requestMapper;
    private final RequestService requestService;

    @PostMapping
    public RequestAnswerDto add(@RequestHeader(name = HEADER_USER) Long userId,
                                @Valid @RequestBody RequestAddDto requestAddDto) {
        log.info("POST /requests");
        return requestMapper.requestToRequestAnswerDto(requestService.add(userId, requestMapper.requestAddDtoToRequest(requestAddDto)));
    }

    @GetMapping
    public List<RequestWithItemDto> getAllRequestByOwnerId(@RequestHeader(name = HEADER_USER) Long userId) {
        log.info("GET /requests");
        return requestService.getAllRequestsByOwnerId(userId);
    }

    @GetMapping("/all")
    public List<RequestWithItemDto> getAllRequest(@RequestHeader(name = HEADER_USER) Long userId,
                                                  @RequestParam(required = false) Integer from,
                                                  @RequestParam(required = false) Integer size) {
        log.info("GET /requests/all?from=" + from + "&size=" + size);
        return requestService.getAllRequest(userId, PaginationMapper.toMakePage(from, size));
    }

    @GetMapping("/{requestId}")
    public RequestWithItemDto getByRequestIdWithItem(@PathVariable Long requestId, @RequestHeader(name = HEADER_USER) Long userId) {
        log.info("GET /requests/" + requestId);
        return requestService.getByRequestIdWithItem(requestId, userId);
    }

}
