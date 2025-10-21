package main.services;

import main.facade.CheckoutFacade;
import main.model.Cart;
import main.model.Customer;
import main.payment.*;

public class CheckoutCoordinator {
    private final Cart cart;
    private final Customer customer;

    public CheckoutCoordinator(Cart cart, Customer customer) {
        this.cart = cart;
        this.customer = customer;
    }

    public boolean process(int paymentIndex, double amount, double discount, double cashback, boolean fraud) {
        var methods = customer.getPaymentMethods();
        if (paymentIndex < 0 || paymentIndex >= methods.size()) {
            System.out.println("Invalid payment method index.");
            return false;
        }

        Payment decorated = methods.get(paymentIndex);
        if (discount > 0)  {
            decorated = new DiscountDecorator(discount, decorated);
        }
        if (cashback > 0)  {
            decorated = new CashbackDecorator(cashback, decorated);
        }
        if (fraud)         {
            decorated = new FraudDetectionDecorator(decorated);
        }

        CheckoutFacade facade = new CheckoutFacade(decorated);
        facade.processPayment(amount, 0, 0);
        return true;
    }
}
