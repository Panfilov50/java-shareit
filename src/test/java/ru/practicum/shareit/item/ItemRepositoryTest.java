package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {
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

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        user1 = userRepository.save(new User(1L, "name1", "mail1@yandex.com"));
        user2 = userRepository.save(new User(2L, "name2", "mail2@yandex.com"));
        request = requestRepository.save(new ItemRequest(1L, "description", user2.getId(), now));
        item = itemRepository.save(new Item(1L, "itemName", "description", true, user1.getId(), request.getId()));
    }


    @Test
    void search() {
        List<Item> res = itemRepository.search("tem", Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(item, res.get(0));
    }

    @Test
    void findAllByRequesterId() {
        List<Item> res = itemRepository.findAllByRequesterId(user2.getId());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(item, res.get(0));
    }

    @Test
    void findAllByRequesterIdIsNot() {
        List<Item> res = itemRepository.findAllByRequesterIdIsNot(user1.getId());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(item, res.get(0));
    }

    @Test
    void findAllByRequestId() {
        List<Item> res = itemRepository.findAllByRequestId(request.getId());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(item, res.get(0));
    }

    @Test
    void findAll() {
        List<Item> res = itemRepository.findAll();
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(item, res.get(0));
    }
}