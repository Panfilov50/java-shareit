package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.Header.HEADER_USER;

@Slf4j
@Validated
@Controller
@AllArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(HEADER_USER) Long userId,
                                      @RequestBody @Valid BookingRequestDto dto) {
        log.info("POST /bookings");
        return bookingClient.add(userId, dto);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getByBookingId(@PathVariable Long bookingId,
                                                 @RequestHeader(HEADER_USER) Long userId) {
        log.info("GET /bookings/" + bookingId);
        return bookingClient.getByBookingId(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approved(@PathVariable Long bookingId,
                                           @RequestHeader(HEADER_USER) Long ownerId,
                                           @RequestParam boolean approved) {
        log.info("PATCH /bookings/" + bookingId + "?approved=" + approved);
        return bookingClient.approved(bookingId, ownerId, approved);
    }

    @GetMapping
    public ResponseEntity<Object> getAllByBooker(@RequestHeader(HEADER_USER) Long bookerId,
                                                 @RequestParam(defaultValue = "ALL") String state,
                                                 @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(name = "size", defaultValue = "20") Integer size) {

        log.info("GET /bookings?state=" + state);
        return bookingClient.getAllByBooker(bookerId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllByOwner(@RequestHeader(HEADER_USER) Long ownerId,
                                                @RequestParam(defaultValue = "ALL") String state,
                                                @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @Positive @RequestParam(name = "size", defaultValue = "20") Integer size) {

        log.info("GET /bookings?state=" + state);
        return bookingClient.getAllByOwner(ownerId, state, from, size);
    }
}
