package Data.userData;

import exception.AadhaarNumIllegalException;
import exception.BalanceNoEnoughException;
import exception.NameIllegalException;
import exception.SexIllegalException;

public class Student extends User {

    private int discount_times;

    public Student(String name, String sex, String aadhaarNum, int discount_times, CertTable certTable) throws NameIllegalException,
            SexIllegalException, AadhaarNumIllegalException {
        super(name, sex, aadhaarNum, certTable);
        this.discount_times = discount_times;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Discount:" + discount_times;
    }

    @Override
    public void pay(Order order) throws BalanceNoEnoughException {
        if (discount_times == 0) {
            super.pay(order);
        } else {
            int discount_bk = discount_times;
            try {
                int num = order.genNum();
                for (int i = 0; i < num; i++) {
                    double cost = order.getPer_cost();
                    if (discount_times > 0) {
                        discount_times--;
                        cost *= 0.05;
                    }
                    if (account.getBalance() < cost)
                        throw new BalanceNoEnoughException();
                    account.cost(cost);
                }
            } catch (BalanceNoEnoughException e) {
                discount_times = discount_bk;
                throw e;
            }
        }
        order.setPaid(true);
    }

}
