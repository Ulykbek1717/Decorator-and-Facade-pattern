package main.payment;

public class FraudDetectionDecorator implements Payment {
    private  Payment base;

    public FraudDetectionDecorator(Payment base) {
        this.base = base;
    }

    
    @Override
    public void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Fraud detected! Invalid amount: $" + amount);
            return;
        }

        System.out.println("Transaction passed check.");
        base.pay(amount);
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
