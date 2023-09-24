package ru.practicum.shareit.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRequestRepository requestRepository;
    private User user1;
    private User user2;
    private Item item;
    private ItemRequest request;
    private Booking booking;
    private Sort sort = Sort.by("start");

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(new User(1L, "name1", "mail1@yandex.com"));
        user2 = userRepository.save(new User(1L, "name2", "mail2@yandex.com"));
        request = requestRepository.save(new ItemRequest(-1L, "description", user2.getId(), LocalDateTime.now()));
        item = itemRepository.save(new Item(1L, "itemName", "description", true, user1.getId(), request.getId()));
        LocalDateTime from = LocalDateTime.now().plusHours(1);
        LocalDateTime till = from.plusDays(1);
        booking = bookingRepository.save(new Booking(1L, from, till, item.getId(), user2.getId(), Status.APPROVED));
    }

    @AfterEach
    void clear() {
       user1 = null;
       user2 = null;
       item = null;
       request = null;
       booking = null;

       userRepository.deleteAll();
       requestRepository.deleteAll();
       itemRepository.deleteAll();
       bookingRepository.deleteAll();
    }

    @Test
    void findByBookerIdAndEndIsBeforeAndStartIsAfter() {
        List<Booking> res = bookingRepository.findByBookerIdAndEndIsBeforeAndStartIsAfter(user2.getId(), LocalDateTime.now().plusHours(3), LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findByBookerIdAndStartAfter() {
        List<Booking> res = bookingRepository.findByBookerIdAndStartAfter(user2.getId(), LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findByBookerIdAndEndAfter() {
        List<Booking> res = bookingRepository.findByBookerIdAndStartAfter(user2.getId(), LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByBookerId() {
        List<Booking> res = bookingRepository.findAllByBookerId(user2.getId(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByBookerIdAndStatus() {
        List<Booking> res = bookingRepository.findAllByBookerIdAndStatus(user2.getId(), Status.APPROVED, Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void bookingsForItem() {
        List<Booking> res = bookingRepository.bookingsForItem(user1.getId(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void allBookingsForItem() {
        List<Booking> res = bookingRepository.allBookingsForItem(item.getId(), sort);
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void bookingsForItemPast() {
        List<Booking> res = bookingRepository.bookingsForItemPast(item.getId(), LocalDateTime.now().plusDays(2), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void bookingsForItemAndBookerPast() {
        List<Booking> res = bookingRepository.bookingsForItemAndBookerPast(user2.getId(), item.getId(), LocalDateTime.now().plusDays(2));
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void bookingsForItemFuture() {
        List<Booking> res = bookingRepository.bookingsForItemFuture(item.getId(), LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void bookingsForItemCurrent() {
        List<Booking> res = bookingRepository.bookingsForItemCurrent(item.getId(), LocalDateTime.now().plusHours(1), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByItemsOwnerId() {
        List<Booking> res = bookingRepository.findAllByItemsOwnerId(user2.getId());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

}