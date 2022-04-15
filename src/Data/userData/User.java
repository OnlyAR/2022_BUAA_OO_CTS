package Data.userData;

import Data.lineData.Line;
import Data.lineData.LineMap;
import Data.lineData.Station;
import Data.trainData.Ticket;
import Data.trainData.Train;
import exception.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class User {

    private String name;
    private char sex;
    private String aadhaarNum;
    private final ArrayList<Order> orderList = new ArrayList<>();


    public User(String name, String sex, String aadhaarNum)
            throws NameIllegalException, SexIllegalException, AadhaarNumIllegalException {
        setName(name);
        setSex(sex);
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
        }
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
        return "Name:"      + name +
               "\nSex:"     + sex +
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
}
