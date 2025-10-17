package payment;


public class CreditCardPayment implements Payment {
    private final String cardName;
    private double balance;

    public CreditCardPayment(String cardName, double balance) {
        this.cardName = cardName;
        this.balance = balance;
    }

    @Override
    public void pay(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.printf("Paid $%.2f via Credit Card [%s].%n", amount, cardName);
        } else {
            System.out.println("nsufficient balance on " + cardName + "!");
        }
    }

    @Override
    public void addCashback(double amount) {
        balance += amount;
        System.out.printf("Cashback $%.2f added to card [%s]. New balance: $%.2f%n", amount, cardName, balance);
    }

    @Override
    public void addMoney(double amount) {
        balance += amount;
        System.out.printf("Added $%.2f to [%s]. Balance: $%.2f%n", amount, cardName, balance);
    }

    @Override
    public String toString() {
        return "CreditCard [" + cardName + "] - $" + balance;
    }
}