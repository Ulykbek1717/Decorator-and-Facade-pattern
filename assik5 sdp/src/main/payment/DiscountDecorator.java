package main.payment;

public class DiscountDecorator implements Payment {
    private final Payment payment;
    private final double discountPercent;

    public DiscountDecorator(double discountPercent, Payment payment) {
        this.discountPercent = discountPercent;
        this.payment = payment;
    }

    @Override
    public void pay(double amount) {
        double discountedAmount = amount * (1 - discountPercent / 100);
        System.out.printf("Applied %.1f%% discount → Final: $%.2f%n", discountPercent, discountedAmount);
        payment.pay(discountedAmount);
    }

    @Override
    public void addCashback(double amount) {
        payment.addCashback(amount);
    }

    @Override
    public void addMoney(double amount) {
        payment.addMoney(amount);
    }
}