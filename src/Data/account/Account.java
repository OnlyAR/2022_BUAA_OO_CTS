package Data.account;

public class Account {
    double balance = 0;
    public void addBalance(double balance) {
        this.balance += balance;
    }

    public double getBalance() {
        return balance;
    }

    public void cost(double cost) {
        balance -= cost;
    }

    @Override
    public String toString() {
        return String.format("Balance:%.2f", balance);
    }
}
