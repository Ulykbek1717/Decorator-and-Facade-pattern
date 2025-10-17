package main.model;


import java.util.*;

public class Cart {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void add(Item item, int qty) {
        if (qty <= 0) return;
        CartItem ci = items.get(item.getId());
        if (ci == null) {
            items.put(item.getId(), new CartItem(item, qty));
        } else {
            ci.setQty(ci.getQty() + qty);
        }
    }

    public boolean remove(int itemId) {
        return items.remove(itemId) != null;
    }

    public boolean updateQty(int itemId, int qty) {
        CartItem ci = items.get(itemId);
        if (ci == null) {
            return false;
        }
        if (qty <= 0) { 
            items.remove(itemId); 
        } else { 
            ci.setQty(qty); 
        }
        return true;
    }

    public double getTotal() {
    double sum = 0;
        for (CartItem item : items.values()) {
            sum += item.getTotal();
        }
        return sum;
    }

    public boolean isEmpty() { 
        return items.isEmpty(); 
    }

    public void clear() { 
        items.clear(); 
    }

    public void print() {
        if (items.isEmpty()) {
            System.out.println("The cart is empty.");
            return;
        }
        System.out.println("Cart:");
        
        for (CartItem ci : items.values()) {
            System.out.println("   " + ci);
        }
        
        System.out.printf("Total: $%.2f%n", getTotal());
    }
}

