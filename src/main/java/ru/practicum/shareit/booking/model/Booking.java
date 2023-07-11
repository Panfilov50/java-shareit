package ru.practicum.shareit.booking.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Booking {
    int id;
    LocalDateTime start;
    LocalDateTime end;
    int booker;

}
