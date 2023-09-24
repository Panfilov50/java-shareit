package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRequestRepository requestRepository;
    private User user1;
    private User user2;
    private Item item;
    private ItemRequest request;
    private Comment comment;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        user1 = userRepository.save(new User(1L, "name1", "mail1@yandex.com"));
        user2 = userRepository.save(new User(2L, "name2", "mail2@yandex.com"));
        request = requestRepository.save(new ItemRequest(1L, "description", user2.getId(), now));
        item = itemRepository.save(new Item(1L, "itemName", "description", true, user1.getId(), request.getId()));
        comment = commentRepository.save(new Comment(1L, "comment", now, item.getId(), user2.getId(), user2.getName()));
    }

    @Test
    void findAllByItemId() {
        List<Comment> res = commentRepository.findAllByItemId(item.getId());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(comment, res.get(0));
    }

}