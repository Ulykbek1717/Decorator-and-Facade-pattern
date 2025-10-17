package payment;


public interface Payment {
    void pay(double amount);
    void addCashback(double amount);
    void addMoney(double amount);
}
