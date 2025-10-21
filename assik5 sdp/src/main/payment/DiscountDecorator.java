package main.payment;

public class DiscountDecorator implements Payment {
    private  Payment base;
    private  double percent;

    public DiscountDecorator(double percent, Payment base) {
        this.percent = percent;
        this.base = base;
    }

    @Override
    public void pay(double amount) {
        double discountedAmount = amount * (1 - percent / 100);
        System.out.println("Discount " + percent + "% applied. Final amount: $" + discountedAmount);
        base.pay(discountedAmount);
    }

    @Override
    public void addCashback(double amount) {
        base.addCashback(amount);
    }

    @Override
    public void addMoney(double amount) {
        base.addMoney(amount);
    }
}
