package main.payment;

public class PayPalPayment implements Payment {
    private  String email;
    private double balance;

    public PayPalPayment(String email, double balance) {
        this.email = email;
        this.balance = balance;
    }

    @Override
    public void pay(double amount) {
        if (balance < amount) {
            System.out.println("Not enough balance in PayPal: " + email);
            return;
        }

        balance -= amount;
        System.out.println("Paid $" + amount + " via PayPal (" + email + ")");
    }

    @Override
    public void addCashback(double amount) {
        balance += amount;
        System.out.println("Cashback $" + amount + " added. New balance: $" + balance);
    }

    @Override
    public void addMoney(double amount) {
        balance += amount;
        System.out.println("Added $" + amount + ". Balance: $" + balance);
        }

    @Override
    public String toString() {
        return "PayPal " + email + "$" + balance;
    }
}
