package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingAnswerDto;
import ru.practicum.shareit.booking.dto.BookingNewAnswerDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.core.mapper.PaginationMapper;

import java.util.List;

import static ru.practicum.shareit.Header.HEADER_USER;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public BookingNewAnswerDto add(@RequestHeader(name = HEADER_USER) long bookerId,
                                   @RequestBody BookingRequestDto dto) {
        log.info("POST /bookings");
        return bookingService.add(bookerId, dto.getItemId(), bookingMapper.bookingRequestDtoToBooking(dto));
    }

    @GetMapping("/{bookingId}")
    public BookingAnswerDto getByBookingId(@PathVariable long bookingId,
                                           @RequestHeader(name = HEADER_USER) long userId) {
        log.info("GET /bookings/" + bookingId);
        return bookingMapper.bookingToBookingAnswerDto(bookingService.getByBookingId(bookingId, userId));
    }

    @PatchMapping("/{bookingId}")
    public BookingAnswerDto approved(@PathVariable long bookingId,
                                     @RequestHeader(name = HEADER_USER) long ownerId,
                                     @RequestParam boolean approved) {
        log.info("PATCH /bookings/" + bookingId + "?approved=" + approved);
        return bookingMapper.bookingToBookingAnswerDto(
                bookingService.approved(bookingId, ownerId, approved));
    }

    @GetMapping
    public List<BookingAnswerDto> getAllByBooker(@RequestHeader(name = HEADER_USER) long bookerId,
                                                 @RequestParam(defaultValue = "ALL") String state,
                                                 @RequestParam(required = false) Integer from,
                                                 @RequestParam(required = false) Integer size) {
        log.info("GET /bookings?state=" + state.toString());
        return bookingMapper.bookingListToListBookingAnswerDto(
                bookingService.getAllBookingByBookerId(bookerId, state, PaginationMapper.toMakePage(from, size)));
    }

    @GetMapping("/owner")
    public List<BookingAnswerDto> getAllByOwner(@RequestHeader(name = HEADER_USER) long ownerId,
                                                @RequestParam(defaultValue = "ALL") String state,
                                                @RequestParam(required = false) Integer from,
                                                @RequestParam(required = false) Integer size) {
        log.info("GET /bookings?state=" + state.toString());
        return bookingMapper.bookingListToListBookingAnswerDto(
                bookingService.getAllBookingByOwnerId(ownerId, state, PaginationMapper.toMakePage(from, size)));
    }
}
