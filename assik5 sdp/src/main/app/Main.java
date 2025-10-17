package main.app;

import main.ui.ConsoleApp;
import main.services.*;
import main.model.*;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("Ulykbek");
        Cart cart = new Cart();

        CatalogService catalog = new CatalogService();
        PaymentService payments = new PaymentService(customer);
        CartService cartService = new CartService(cart);
        CheckoutCoordinator checkout = new CheckoutCoordinator(cart, customer);

        ConsoleApp app = new ConsoleApp(catalog, cartService, payments, checkout);
        app.run(); 
    }
}
