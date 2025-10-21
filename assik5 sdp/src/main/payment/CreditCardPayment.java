package main.payment;

public class CreditCardPayment implements Payment {
    private  String name;
    private double balance;

    public CreditCardPayment(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }


    @Override
    public void pay(double amount) {
        if (balance < amount) {
            System.out.println("Not enough money on card: " + name);
            return;
        }

        balance -= amount;
        System.out.println("Paid $" + amount + " by card: " + name);
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
        return name + " | balance: $" + balance;
    }
}
