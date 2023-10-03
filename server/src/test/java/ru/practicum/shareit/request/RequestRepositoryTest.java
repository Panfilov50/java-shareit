package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RequestRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;

    private User user1;
    private User user2;
    private Request request;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        user1 = userRepository.save(new User(1L, "name1", "mail1@yandex.com"));
        user2 = userRepository.save(new User(2L, "name2", "mail2@yandex.com"));
        request = requestRepository.save(new Request(1L, "description",now, user2));
        Item item = itemRepository.save(new Item(1L, "itemName", "description", true, user1, null, null, null, request));
        commentRepository.save(new Comment(1L, "comment", item, user2, now));
    }

    @Test
    void findAllByUserIdOrderByCreatedDesc() {
        List<Request> res = requestRepository.findAllByUserIdOrderByCreatedDesc(user2.getId());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(request, res.get(0));
    }

    @Test
    void findAllByUserIdNotOrderByCreatedDesc() {
        List<Request> res = requestRepository.findAllByUserIdNotOrderByCreatedDesc(user1.getId(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(request, res.get(0));
    }
}