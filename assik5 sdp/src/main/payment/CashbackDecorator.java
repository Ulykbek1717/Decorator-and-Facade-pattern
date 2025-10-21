package main.payment;

public class CashbackDecorator implements Payment {
    private  double percent;
    private  Payment base;

    public CashbackDecorator(double percent, Payment base) {
        this.percent = percent;
        this.base = base;
    }

    @Override
    public void pay(double amount) {
        base.pay(amount);
        double cashbackAmount = amount * percent / 100;
        System.out.println("Cashback earned: $" + String.format("%.2f", cashbackAmount));
        base.addCashback(cashbackAmount);
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
