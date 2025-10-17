package main.ui;

import main.services.*;
import main.model.Item;

public class MenuRouter {
    private final java.util.Scanner sc;
    private final CatalogService catalog;
    private final CartService cart;
    private final PaymentService pay;
    private final CheckoutCoordinator checkout;

    public MenuRouter(java.util.Scanner sc, CatalogService catalog, CartService cart, PaymentService pay, CheckoutCoordinator checkout) {
        this.sc = sc;
        this.catalog = catalog;
        this.cart = cart;
        this.pay = pay;
        this.checkout = checkout;
    }

    public void catalogMenu() {
        while (true) {
            System.out.println("\nCatalog:");
            catalog.list().forEach(System.out::println);
            System.out.println("[a] Add to cart  [r] Remove  [n] New  [b] Back");
            System.out.print("--> ");
            switch (sc.nextLine().trim().toLowerCase()) {
                case "a" -> {
                    int id = Input.askInt(sc, "Item ID: ");
                    int qty = Input.askInt(sc, "Quantity: ", v -> v > 0, "Must be > 0");
                    catalog.get(id).ifPresentOrElse(
                        it -> { cart.add(it, qty); System.out.println("Added."); },
                        () -> System.out.println("Item not found.")
                    );
                }
                case "r" -> {
                    int id = Input.askInt(sc, "ID to remove: ");
                    System.out.println(catalog.remove(id) ? "Removed." : "Item not found.");
                }
                case "n" ->  {
                    int id = Input.askInt(sc, "New ID: ", v -> !catalog.exists(v), "ID already exists.");
                    String name = Input.askLine(sc, "Name: ");
                    double price = Input.askDouble(sc, "Price: ", v -> v >= 0, "Price must be >= 0");
                    catalog.add(new Item(id, name, price));
                    System.out.println("Item added.");
                }
                case "b" -> {
                    return;
                }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    public void cartMenu() {
        while (true) {
            System.out.println("\nCart:");
            cart.print();
            System.out.println("[u] Update qty  [d] Delete  [c] Clear  [b] Back");
            System.out.print("> ");
            switch (sc.nextLine().trim().toLowerCase()) {
                case "u" -> {
                    int id = Input.askInt(sc, "Item ID: ");
                    int qty = Input.askInt(sc, "New quantity: ", v -> v >= 0, "Must be >= 0");
                    System.out.println(cart.updateQty(id, qty) ? "Updated." : "Item not found in cart.");
                }
                case "d" -> {
                    int id = Input.askInt(sc, "Item ID: ");
                    System.out.println(cart.remove(id) ? "Removed." : "Item not found in cart.");
                }
                case "c" -> {
                    cart.clear();
                    System.out.println("Cart cleared.");
                }
                case "b" -> {
                    return;
                }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    public void paymentsMenu() {
        while (true) {
            System.out.println("\nPayment Methods:");
            pay.show();
            System.out.println("[a] Add Card  [p] Add PayPal  [m] Add Money  [b] Back");
            System.out.print("--> ");
            switch (sc.nextLine().trim().toLowerCase()) {
                case "a" -> {
                    String name = Input.askLine(sc, "Card name (VISA): ");
                    double bal = Input.askDouble(sc, "Initial balance: ");
                    pay.addCard(name, bal);
                    System.out.println("Card added.");
                }
                case "p" -> {
                    String email = Input.askLine(sc, "PayPal email: ");
                    double bal = Input.askDouble(sc, "Initial balance: ");
                    pay.addPayPal(email, bal);
                    System.out.println("PayPal added.");
                }
                case "m" -> {
                    pay.show();
                    int idx = Input.askInt(sc, "Select method number to top up: ") - 1;
                    double amount = Input.askDouble(sc, "Top-up amount: ", v -> v > 0, "Must be > 0");
                    pay.topUp(idx, amount);
                }
                case "b" -> {
                    return; 
                }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    public void checkoutMenu() {
        if (cart.isEmpty()) { System.out.println("Cart is empty."); return; }
        pay.show();
        int idx = Input.askInt(sc, "Select payment method: ") - 1;

        double amount = cart.total();
        System.out.printf("Amount due (no discounts): $%.2f%n", amount);

        double discount = Input.askDouble(sc, "Discount %% ", v -> v >= 0, "Must be >= 0");
        double cashback = Input.askDouble(sc, "Cashback %% ", v -> v >= 0, "Must be >= 0");
        boolean fraud = Input.askYesNo(sc, "Enable Check? [y/n]: ");

        boolean ok = checkout.process(idx, amount, discount, cashback, fraud);
        if (ok) {
            System.out.println("Payment processed successfully.");
            cart.clear();
        }
    }
}
