package ru.practicum.shareit.booking.controller;

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
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.FullBookingDto;
import ru.practicum.shareit.booking.model.enums.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.Header.HEADER_USER;

@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<FullBookingDto> createBooking(@Valid @RequestBody BookingDto dto,
                                                        @RequestHeader(HEADER_USER) long bookerId) {

        return new ResponseEntity<>(bookingService.addBooking(dto, bookerId), HttpStatus.CREATED);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<FullBookingDto> approveBooking(@PathVariable long bookingId,
                                                         @RequestParam boolean approved,
                                                         @RequestHeader(HEADER_USER) long bookerId) {

        return new ResponseEntity<>(bookingService.approveBooking(bookingId, approved, bookerId), HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<FullBookingDto> getBooking(@PathVariable long bookingId,
                                                     @RequestHeader(HEADER_USER) long bookerId) {

        return new ResponseEntity<>(bookingService.getBooking(bookingId, bookerId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FullBookingDto>> getAllBookingsByBookerId(@RequestHeader(HEADER_USER) long bookerId,
                                                                        @RequestParam(defaultValue = "ALL") BookingState state) {

        return new ResponseEntity<>(bookingService.getAllBookingsByBookerId(bookerId, state), HttpStatus.OK);
    }

    @GetMapping("/owner")
    public ResponseEntity<List<FullBookingDto>> getAllBookingItemsByBookerId(@RequestHeader(HEADER_USER) long ownerId,
                                                                             @RequestParam(defaultValue = "ALL") BookingState state) {

        return new ResponseEntity<>(bookingService.getAllBookingByItemsByOwnerId(ownerId, state), HttpStatus.OK);
    }

}

