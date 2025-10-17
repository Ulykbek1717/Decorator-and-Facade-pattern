package payment;


public class CashbackDecorator implements Payment {
    private final double cashbackPercent;
    private final Payment payment;

    public CashbackDecorator(double cashbackPercent, Payment payment) {
        this.cashbackPercent = cashbackPercent;
        this.payment = payment;
    }

    @Override
    public void pay(double amount) {
        payment.pay(amount);
        double cashbackAmount = amount * cashbackPercent / 100;
        System.out.printf("Cashback earned: $%.2f%n", cashbackAmount);
        payment.addCashback(cashbackAmount);
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
