package main.services;

import main.model.Cart;
import main.model.Item;

public class CartService {
    private final Cart cart;
    public CartService(Cart cart) { 
        this.cart = cart; 
    }

    public void add(Item item, int qty) {
        cart.add(item, qty);
    }
    public boolean updateQty(int id, int qty) {
        return cart.updateQty(id, qty);
    }
    public boolean remove(int id) {
        return cart.remove(id);
    }
    public void clear() {
        cart.clear();
    }
    public boolean isEmpty() {
        return cart.isEmpty();
    }
    public double total() {
        return cart.getTotal();
    }
    public void print() { cart.print(); }
}
