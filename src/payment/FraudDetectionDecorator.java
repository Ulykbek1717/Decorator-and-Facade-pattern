package payment;


public class FraudDetectionDecorator implements Payment {
    private final Payment payment;

    public FraudDetectionDecorator(Payment payment) {
        this.payment = payment;
    }

    private boolean isSafe(double amount) {
        return amount > 0 && amount <= 100_000;
    }

    @Override
    public void pay(double amount) {
        if (isSafe(amount)) {
            System.out.println("Transaction passed fraud check.");
            payment.pay(amount);
        } else {
            System.out.printf("Fraud detected! Suspicious payment: $%.2f%n", amount);
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