package app;

import facade.CheckoutFacade;
import model.Cart;
import model.Customer;
import model.Item;
import payment.*;

import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    private static final Map<Integer, Item> catalog = new LinkedHashMap<>();

    private static final Customer customer = new Customer("Ulykbek");
    private static final Cart cart = new Cart();

    public static void main(String[] args) {
        seedCatalog();
        seedPayments();

        while (true) {
            printMainMenu();
            String cmd = sc.nextLine().trim();
            switch (cmd) {
                case "1" -> catalogMenu();
                case "2" -> cartMenu();
                case "3" -> paymentsMenu();
                case "4" -> checkoutMenu();
                case "0" -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n E-Commerce CLI");
        System.out.println("Customer: " + customer);
        System.out.println("[1] Catalog");
        System.out.println("[2] Cart");
        System.out.println("[3] Payment Methods");
        System.out.println("[4] Checkout");
        System.out.println("[0] Exit");
        System.out.print("> ");
    }

    /*CATALOG*/

    private static void seedCatalog() {
        catalog.put(1, new Item(1, "Keyboard", 49.99));
        catalog.put(2, new Item(2, "Mouse", 19.99));
        catalog.put(3, new Item(3, "Monitor 24\"", 179.00));
        catalog.put(4, new Item(4, "USB-C Cable", 8.50));
        catalog.put(5, new Item(5, "Headset", 39.90));
    }

    private static void catalogMenu() {
        while (true) {
            System.out.println("\nCatalog");

            catalog.values().forEach(System.out::println);
            System.out.println("[a] Add to Cart   [r] Remove from Catalog   [n] New Item   [b] Back");
            System.out.print("--> ");
            String cmd = sc.nextLine().trim().toLowerCase();
            switch (cmd) {
                case "a" -> addToCartFlow();
                case "r" -> removeItemFromCatalog();
                case "n" -> createNewItem();
                case "b" -> { return; }
                default -> System.out.println("Unrecognized command.");
            }
        }
    }

    private static void addToCartFlow() {
        int id = askInt("Item ID: ");
        Item item = catalog.get(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        int qty = askInt("Quantity: ");

        if (qty <= 0) {
            System.out.println("Quantity must be > 0");
            return; }

        cart.add(item, qty);
        System.out.println("Added to cart.");
    }

    private static void removeItemFromCatalog() {
        int id = askInt("Item ID to remove: ");
        if (catalog.remove(id) != null) {
            System.out.println("Removed from catalog.");
        }
        else {
            System.out.println("Item not found.");
        }
    }

    private static void createNewItem() {
        int id = askInt("New ID: ");
        if (catalog.containsKey(id)) {
            System.out.println("ID already exists.");
            return; }

        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        double price = askDouble("Price: ");
        catalog.put(id, new Item(id, name, price));
        System.out.println("Item added.");
    }

    /*CART*/

    private static void cartMenu() {
        while (true) {
            System.out.println("\nCart");
            cart.print();
            System.out.println("[u] Change quantity   [d] Remove item   [c] Clear   [b] Back");
            System.out.print("> ");
            String cmd = sc.nextLine().trim().toLowerCase();
            switch (cmd) {
                case "u" -> {
                    int id = askInt("Item ID: ");
                    int qty = askInt("New quantity: ");
                    if (cart.updateQty(id, qty)){
                        System.out.println("Updated.");
                    }
                    else {
                        System.out.println("Item with such ID not found in the cart.");
                    }
                }
                case "d" -> {
                    int id = askInt("Item ID: ");
                    if (cart.remove(id)) {
                        System.out.println("Removed.");
                    }
                    else {
                        System.out.println("Item with such ID not found in the cart.");
                    }
                }
                case "c" -> {
                    cart.clear();
                    System.out.println("Cart cleared."); }
                case "b" -> { return; }
                default -> System.out.println("Unrecognized command.");
            }
        }
    }

    /*PAYMENTS*/

    private static void seedPayments() {

        customer.addPaymentMethod(new CreditCardPayment("VISA", 500.00));
        customer.addPaymentMethod(new PayPalPayment("user@example.com", 300.00));
    }

    private static void paymentsMenu() {
        while (true) {
            System.out.println("\nPayment Methods ");
            customer.showPaymentMethods();
            System.out.println("[a] Add Card   [p] Add PayPal   [m] Add  Money   [b] Back");
            System.out.print("--> ");
            String cmd = sc.nextLine().trim().toLowerCase();
            switch (cmd) {
                case "a" -> addCard();
                case "p" -> addPayPal();
                case "m" -> addMoneyFlow();
                case "b" -> { return; }
                default -> System.out.println("Unrecognized command.");
            }
        }
    }

    private static void addCard() {
        System.out.print("Card name (VISA): ");
        String name = sc.nextLine().trim();
        double bal = askDouble("Initial balance: ");
        customer.addPaymentMethod(new CreditCardPayment(name, bal));
        System.out.println("Card added.");
    }

    private static void addPayPal() {
        System.out.print("PayPal Email: ");
        String email = sc.nextLine().trim();
        double bal = askDouble("Initial balance: ");
        customer.addPaymentMethod(new PayPalPayment(email, bal));
        System.out.println("PayPal added.");
    }

    private static void addMoneyFlow() {
        customer.showPaymentMethods();
        int idx = askInt("Select payment method to top up: ") - 1;

        List<Payment> methods = customer.getPaymentMethods();
        if (idx < 0 || idx >= methods.size()) {
            System.out.println("Invalid number.");
            return;
        }

        double amount = askDouble("Top-up amount: ");
        methods.get(idx).addMoney(amount);
    }

    /*CHECKOUT*/

    private static void checkoutMenu() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return; }

        customer.showPaymentMethods();
        int idx = askInt("Select payment method: ") - 1;

        List<Payment> methods = customer.getPaymentMethods();
        if (idx < 0 || idx >= methods.size()) {
            System.out.println("Invalid number.");
            return; }

        double amount = cart.getTotal();
        System.out.printf("Amount due (without discount): $%.2f%n", amount);

        double discount = askDouble("Discount % (0 for no discount): ");
        double cashback = askDouble("Cashback % (0 for no cashback): ");
        boolean fraud = askYesNo("Enable Fraud check? (y/n): ");

        Payment base = methods.get(idx);
        Payment decorated = base;

        if (discount > 0) {
            decorated = new DiscountDecorator(discount, decorated);
        }
        if (cashback > 0) {
            decorated = new CashbackDecorator(cashback, decorated);
        }
        if (fraud) {
            decorated = new FraudDetectionDecorator(decorated);
        }

        CheckoutFacade facade = new CheckoutFacade(decorated);

        System.out.println("\nCheckout");
        facade.processPayment(amount, 0, 0); // decorators have already been manually applied
        if (cashback > 0) {
            // Cashback is handled by CashbackDecorator
            System.out.printf("The cashback is stored on the selected payment method.%n");
        }
        cart.clear();
    }

    /*Input Utilities*/

    private static int askInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { System.out.println("Please enter an integer."); }
        }
    }

    private static double askDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(sc.nextLine().trim().replace(',', '.')); }
            catch (Exception e) { System.out.println("Please enter a number (e.g., 12.5)."); }
        }
    }

    private static boolean askYesNo(String prompt) {
        System.out.print(prompt);
        String s = sc.nextLine().trim().toLowerCase();
        return s.startsWith("y") || s.equals("yes");
    }
}
