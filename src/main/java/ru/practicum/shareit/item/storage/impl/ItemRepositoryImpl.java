package ru.practicum.shareit.item.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemRepository;

import java.util.*;

@Slf4j
@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private Long id = 1L;

    @Override
    public List<Item> getAll() {
        log.info("Список вещей передан");
        return new ArrayList<>(items.values());
    }

    @Override
    public Optional<Item> getById(Long id) {
        log.info("Вещь {} передана ", id);
        return items.get(id) != null ? Optional.of(items.get(id)) : Optional.empty();
    }

    @Override
    public Item create(Item item) {
        item.setId(id);
        id++;
        items.put(item.getId(), item);
        log.info("Вещь {} создана ", item.toString());
        return item;
    }

    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        log.info("Вещь {} обновлена ", item.toString());
        return item;
    }

    @Override
    public void delete(Long id) {
        log.info("Вещь {} удалена ", id);
        items.remove(id);
    }
}