package main.ui;

import main.services.*;
import java.util.Scanner;

public class ConsoleApp {
    private final Scanner sc = new Scanner(System.in);
    private final MenuRouter router;

    public ConsoleApp(CatalogService catalog, CartService cart, PaymentService pay, CheckoutCoordinator checkout) {
        this.router = new MenuRouter(sc, catalog, cart, pay, checkout);
    }

    public void run() {
        while (true) {
            System.out.println("\nE-Commerce Payment & Discount System");
            System.out.println("[1] Catalog");
            System.out.println("[2] Cart");
            System.out.println("[3] Payment Methods");
            System.out.println("[4] Checkout");
            System.out.println("[0] Exit");
            System.out.print("> ");

            switch (sc.nextLine().trim()) {
                case "1" : {
                    router.catalogMenu();
                    break;
                }
                case "2" : {
                    router.cartMenu();
                    break;
                }
                case "3" : {
                    router.paymentsMenu();
                    break;
                }
                case "4" : {
                    router.checkoutMenu();
                    break;
                }
                case "0" : {
                    System.out.println("Goodbye!");
                    return;
                }
                default : System.out.println("Unknown command.");
            }
        }
    }
}
