package facade;

import payment.CashbackDecorator;
import payment.DiscountDecorator;
import payment.FraudDetectionDecorator;
import payment.Payment;

public class CheckoutFacade {
    private Payment basePayment;

    public CheckoutFacade(Payment payment) {
        this.basePayment = payment;
    }

    public void processPayment(double amount, double discountPercent, double cashbackPercent) {
        Payment decorated = basePayment;

        if (discountPercent > 0)
            decorated = new DiscountDecorator(discountPercent, decorated);
        if (cashbackPercent > 0)
            decorated = new CashbackDecorator(cashbackPercent, decorated);

        decorated = new FraudDetectionDecorator(decorated);

        System.out.println("\n🛒 ---- Checkout Started ----");
        decorated.pay(amount);
        sendReceipt(amount);
        sendNotification();
        System.out.println("✅ ---- Checkout Completed ----\n");
    }

    private void sendReceipt(double amount) {
        System.out.printf("🧾 Receipt: Payment of $%.2f processed.%n", amount);
    }

    private void sendNotification() {
        System.out.println("📩 Notification: Thank you for shopping with us!");
    }
}