package Data.userData;

import Data.account.Account;
import Data.lineData.Line;
import Data.lineData.LineMap;
import Data.lineData.Station;
import Data.trainData.Ticket;
import Data.trainData.Train;
import exception.*;

import java.util.ArrayList;
import java.util.ListIterator;

public class User {

    protected String name;
    private char sex;
    private String aadhaarNum;
    private final CertTable certTable;
    protected Account account = new Account();
    private final ArrayList<Order> orderList = new ArrayList<>();


    public User(String name, String sex, String aadhaarNum, CertTable certTable)
            throws NameIllegalException, SexIllegalException, AadhaarNumIllegalException {
        setName(name);
        setSex(sex);
        this.certTable = certTable;
        setAadhaarNum(aadhaarNum);
    }

    public void buyTicket(LineMap lineMap, String[] args) throws ArgsIllegalException {
        try {
            String trainId = args[0];
            String start = args[1];
            String end = args[2];
            String seat = args[3];
            String numStr = args[4];
            if (!lineMap.containsTrain(trainId))
                throw new TrainNonExistException();
            Line line = lineMap.getLine(trainId);
            Train train = line.getTrain(trainId);
            if (!line.containsStation(start) || !line.containsStation(end))
                throw new StationNonExistException();
            Station startStation = line.getStation(start);
            Station endStation = line.getStation(end);
            if (!train.containSeat(seat))
                throw new SeatUnmachedException();
            if ((seat.equals("1A") || seat.equals("2A")) && !isNegative())
                throw new CertIllegalException();
            int num = Integer.parseInt(numStr);
            if (num <= 0)
                throw new TicketNumIllegalException();
            Ticket ticket = train.getTicket(seat);
            ticket.sell(num);
            orderList.add(new Order(train, startStation, endStation, ticket, num));
            System.out.println("Thanks for your order");
        } catch (TrainNonExistException e) {
            System.out.println("Train does not exist");
        } catch (StationNonExistException e) {
            System.out.println("Station does not exist");
        } catch (SeatUnmachedException e) {
            System.out.println("Seat does not match");
        } catch (NumberFormatException | TicketNumIllegalException e) {
            System.out.println("Ticket number illegal");
        } catch (TicketUnenoughException e) {
            System.out.println("Ticket does not enough");
        } catch (CertIllegalException e) {
            System.out.println("Cert illegal");
        }
    }

    private boolean isNegative() {
        return certTable.certMap.containsKey(aadhaarNum) && !certTable.certMap.get(aadhaarNum);
    }

    public void setName(String name) throws NameIllegalException {
        if (!nameLegal(name))
            throw new NameIllegalException();
        this.name = name;
    }

    public void setSex(String sex) throws SexIllegalException {
        if (!sexLegal(sex))
            throw new SexIllegalException();
        this.sex = sex.charAt(0);
    }

    public void setAadhaarNum(String aadhaarNum) throws AadhaarNumIllegalException {
        if (!AadhaarNum.legal(aadhaarNum))
            throw new AadhaarNumIllegalException();
        int sexCode = aadhaarNum.charAt(aadhaarNum.length() - 1) - '0';
        if (!(sexCode == 0 && sex == 'F' || sexCode == 1 && sex == 'M' || sexCode == 2 && sex == 'O'))
            throw new AadhaarNumIllegalException();
        this.aadhaarNum = aadhaarNum;
    }

    boolean nameLegal(String name) {
        for (int i = 0; i < name.length(); i++)
            if (!(Character.isAlphabetic(name.charAt(i)) || name.charAt(i) == '_'))
                return false;
        return true;
    }

    boolean sexLegal(String sex) {
        return sex.equals("F") || sex.equals("M") || sex.equals("O");
    }

    public String getAadhaarNum() {
        return aadhaarNum;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name:" + name +
                "\nSex:" + sex +
                "\nAadhaar:" + aadhaarNum;
    }

    public void listOrder() {
        if (orderList.isEmpty())
            System.out.println("No order");
        else {
            ListIterator<Order> iterator = orderList.listIterator(orderList.size());
            while (iterator.hasPrevious()) {
                Order order = iterator.previous();
                System.out.println(order);
            }
        }
    }

    public void rechargeBalance(String[] args) throws ArgsIllegalException {
        try {
            double balance = Double.parseDouble(args[0]);
            if (balance < 0)
                throw new ArgsIllegalException();
            account.addBalance(balance);
            System.out.println("Recharge Success");
        } catch (NumberFormatException e) {
            throw new ArgsIllegalException();
        }
    }

    public void checkBalance() {
        System.out.println("UserName:" + name + "\n" + account.toString());
    }

    public void cancelOrder(String[] args) throws ArgsIllegalException {
        try {
            String train = args[0];
            String start = args[1];
            String end = args[2];
            String seat = args[3];
            int num = Integer.parseInt(args[4]);
            boolean find = false;

            ListIterator<Order> iterator = orderList.listIterator(orderList.size());
            while (iterator.hasPrevious()) {
                Order order = iterator.previous();
                if (order.check(train, start, end, seat) && !order.getPaid()) {
                    find = true;
                    int curNum = order.genNum();
                    if (curNum <= num) {
                        order.delete(curNum);
                        orderList.remove(order);
                        num -= curNum;
                    }
                    else {
                        order.delete(num);
                        num = 0;
                    }
                    if (num == 0)
                        break;
                }
            }
            if (!find)
                throw new NoSuchRecordException();
            if (num > 0)
                throw new NoEnoughOrdersException();
            System.out.println("Cancel success");
        } catch (NumberFormatException e) {
            throw new ArgsIllegalException();
        } catch (NoEnoughOrdersException e) {
            System.out.println("No enough orders");
        } catch (NoSuchRecordException e) {
            System.out.println("No such Record");
        }
    }

    public void payOrder() {
        try {
            if (orderList.size() == 0)
                throw new NoOrderException();
            ListIterator<Order> iterator = orderList.listIterator(orderList.size());
            while (iterator.hasPrevious()) {
                Order order = iterator.previous();
                if (!order.getPaid())
                    pay(order);
            }
            System.out.println("Payment success");
        } catch (NoOrderException e) {
            System.out.println("No order");
        } catch (BalanceNoEnoughException e) {
            System.out.println("Balance does not enough");
        }
    }

    public void pay(Order order) throws BalanceNoEnoughException {
        if (account.getBalance() < order.getCost()) {
            throw new BalanceNoEnoughException();
        }
        account.cost(order.getCost());
        order.setPaid(true);
    }
}
