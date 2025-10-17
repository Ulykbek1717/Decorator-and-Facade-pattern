package main.services;

import main.model.Customer;
import main.payment.*;
import java.util.List;

public class PaymentService {
    private final Customer customer;

    public PaymentService(Customer customer) {
        this.customer = customer;
        seed();
    }

    private void seed() {
        customer.addPaymentMethod(new CreditCardPayment("VISA", 500.00));
        customer.addPaymentMethod(new PayPalPayment("user@example.com", 300.00));
    }

    public void addCard(String name, double bal) { 
        customer.addPaymentMethod(new CreditCardPayment(name, bal));
    }
    public void addPayPal(String email, double bal) {
        customer.addPaymentMethod(new PayPalPayment(email, bal));
    }
    public void topUp(int index, double amount) {
        var list = customer.getPaymentMethods();
        if (index < 0 || index >= list.size()) {
            System.out.println("Invalid number.");
            return;
        }
        list.get(index).addMoney(amount);
    }
    public void show() { 
        customer.showPaymentMethods();
    }
    public List<Payment> methods() {
        return customer.getPaymentMethods();
    }
}
