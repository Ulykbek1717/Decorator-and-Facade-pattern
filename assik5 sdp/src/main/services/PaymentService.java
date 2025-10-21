package main.services;

import main.model.Customer;
import main.payment.*;
import java.util.List;

public class PaymentService {
    private   Customer customer;

    public PaymentService(Customer customer) {
        this.customer = customer;
        DefaultMethods();
    }

    private void DefaultMethods() {
        customer.addPaymentMethod(new CreditCardPayment("VISA", 500.00));
        customer.addPaymentMethod(new PayPalPayment("dovutbekov@inbox.ru", 300.00));
    }

    public void addCard(String name, double balance) {
        customer.addPaymentMethod(new CreditCardPayment(name, balance));
    }
    public void addPayPal(String email, double balance) {
        customer.addPaymentMethod(new PayPalPayment(email, balance));
    }
    public void topUp(int index, double amount) {
        List<Payment> list = customer.getPaymentMethods();

        if (index < 0 || index >= list.size()) {
            System.out.println("Invalid payment method number.");
            return;
        }

        list.get(index).addMoney(amount);
    }
    public void show() { 
        customer.showPaymentMethods();
    }
    public List<Payment> getMethods() {
        return customer.getPaymentMethods();
    }
}
