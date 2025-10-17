package main.services;

import main.model.Item;
import java.util.*;

public class CatalogService {
    private final Map<Integer, Item> catalog = new LinkedHashMap<>();

    public CatalogService() { seed(); }

    private void seed() {
        add(new Item(1, "Keyboard ", 50.00));
        add(new Item(2, "Mouse ", 20.00));
        add(new Item(3, "USB-C  ", 9.00));
    }

    public List<Item> list() {
        return new ArrayList<>(catalog.values());
    }
    public Optional<Item> get(int id) {
        return Optional.ofNullable(catalog.get(id));
    }
    public boolean remove(int id) {
        return catalog.remove(id) != null;
    }
    public void add(Item item) {
        catalog.put(item.getId(), item);
    }
    public boolean exists(int id) {
        return catalog.containsKey(id);
    }
}
