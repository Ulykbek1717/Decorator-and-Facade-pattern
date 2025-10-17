package main.payment;

public class PayPalPayment implements Payment {
    private final String email;
    private double balance;

    public PayPalPayment(String email, double balance) {
        this.email = email;
        this.balance = balance;
    }

    @Override
    public void pay(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.printf("Paid $%.2f via PayPal (%s).%n", amount, email);
        } else {
            System.out.println("Not enough PayPal balance!");
        }
    }

    @Override
    public void addCashback(double amount) {
        balance += amount;
        System.out.printf("Cashback $%.2f added to PayPal (%s). New balance: $%.2f%n", amount, email, balance);
    }

    @Override
    public void addMoney(double amount) {
        balance += amount;
        System.out.printf("Added $%.2f to PayPal (%s). Balance: $%.2f%n", amount, email, balance);
    }

    @Override
    public String toString() {
        return "PayPal " + email + "$" + balance;
    }
}