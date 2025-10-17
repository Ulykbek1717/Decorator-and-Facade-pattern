package main.payment;

public class FraudDetectionDecorator implements Payment {
    private final Payment payment;

    public FraudDetectionDecorator(Payment payment) {
        this.payment = payment;
    }

    private boolean isSafe(double amount) {
    if (amount <= 0) {
        System.out.println("Amount must be greater than 0");
        return false;
    }

    return true;
}


    @Override
    public void pay(double amount) {
        if (isSafe(amount)) {
            System.out.println("Transaction passed check.");
            payment.pay(amount);
        } else {
            System.out.printf("Fraud detected! $%.2f%n", amount);
        }
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