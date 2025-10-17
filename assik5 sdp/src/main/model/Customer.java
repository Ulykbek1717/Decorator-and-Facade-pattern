package main.model;


import main.payment.Payment;
import java.util.*;

public class Customer {
    private final String name;
    private double cashback;
    private final List<Payment> paymentMethods = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
        this.cashback = 0;
    }

    
    public void addPaymentMethod(Payment payment) {
        paymentMethods.add(payment);
    }

    public List<Payment> getPaymentMethods() {
        return paymentMethods;
    }

    public void showPaymentMethods() {
        if (paymentMethods.isEmpty()) {
            System.out.println("No payment methods yet.");
            return;
        }
        for (int i = 0; i < paymentMethods.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, paymentMethods.get(i).toString());
        }
    }

    @Override
    public String toString() {
        return String.format("%s | Cashback: $%.2f | Cards: %d",
                name, cashback, paymentMethods.size());
    }
}