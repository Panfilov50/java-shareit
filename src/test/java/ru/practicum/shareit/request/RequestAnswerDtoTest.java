package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.request.dto.RequestAnswerDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class RequestAnswerDtoTest {

    @Autowired
    private JacksonTester<RequestAnswerDto> json;

    @Test
    void RequestAnswerDtoTest() throws Exception {
        RequestAnswerDto requestAnswerDto = new RequestAnswerDto(1L, "description", LocalDateTime.of(2000, 1, 1, 0, 0, 0));

        JsonContent<RequestAnswerDto> result = json.write(requestAnswerDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2000-01-01T00:00:00");
    }
}

