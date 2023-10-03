package ru.practicum.shareit.core.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ValidationErrorResponse {
    private final Map<String, String> errors;
}
