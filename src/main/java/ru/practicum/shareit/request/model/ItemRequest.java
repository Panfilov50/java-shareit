package ru.practicum.shareit.request.model;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ItemRequest {
    @NotNull
    private long id;
    @NotBlank
    private String description;
    private User requestor;
    private LocalDateTime created;
}
