package main.model;


public class CartItem {
    private final Item item;
    private int qty;

    public CartItem(Item item, int qty) {
        this.item = item;
        this.qty = qty;
    }

    public Item getItem() { 
        return item; 
    }
    public int getQty() { 
        return qty; 
    }
    public void setQty(int qty) { 
        this.qty = qty; 
    }

    public double getTotal() { 
        return item.getPrice() * qty; 
    }

    @Override
    public String toString() {
        return String.format("%s × %d = $%.2f", item.toString(), qty, getTotal());
    }
}

