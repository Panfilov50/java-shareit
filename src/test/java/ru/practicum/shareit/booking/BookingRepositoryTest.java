package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private RequestRepository requestRepository;

    private User user1;
    private User user2;
    private Item item;
    private Request request;
    private Booking booking;

    private Sort sort = Sort.by("start");

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(new User(1L, "name1", "mail1@yandex.com"));
        user2 = userRepository.save(new User(2L, "name2", "mail2@yandex.com"));
        request = requestRepository.save(new Request(1L, "description",LocalDateTime.now(), user2));
        item = itemRepository.save(new Item(1L, "itemName", "description", true, user1, null, null, null, request));
        LocalDateTime from = LocalDateTime.now().plusMinutes(1);
        LocalDateTime till = from.plusHours(2);
        booking = bookingRepository.save(new Booking(1L, from, till, item, user2, BookingStatus.APPROVED));
    }

    @Test
    void findAllByBookerIdOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByBookerIdOrderByStartDesc(user2.getId(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(user2.getId(),LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(1), Pageable.unpaged() );
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByBookerIdAndEndBeforeOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(user2.getId(), LocalDateTime.now().plusHours(3), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByBookerIdAndStartAfterOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(user2.getId(), LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByBookerIdAndStatusOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(user2.getId(), BookingStatus.APPROVED, Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByItemOwnerIdOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(user1.getId(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(user1.getId(),LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(1), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByItemOwnerIdAndEndBeforeOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(user1.getId(), LocalDateTime.now().plusHours(3), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByItemOwnerIdAndStartAfterOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(user1.getId(),LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByItemOwnerIdAndStatusOrderByStartDesc() {
        List<Booking> res = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(user1.getId(),BookingStatus.APPROVED, Pageable.unpaged());
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }

    @Test
    void findAllByItemIdAndStatusOrderByStartAsc() {
        List<Booking> res = bookingRepository.findAllByItemIdAndStatusOrderByStartAsc(item.getId(),BookingStatus.APPROVED);
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals(booking, res.get(0));
    }
}