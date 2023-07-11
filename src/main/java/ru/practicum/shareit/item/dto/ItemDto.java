package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.request.model.ItemRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@Builder(toBuilder = true)
public class ItemDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private ItemRequest request;
}
