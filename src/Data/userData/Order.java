package Data.userData;

import Data.account.Account;
import Data.lineData.Line;
import Data.lineData.Station;
import Data.trainData.Ticket;
import Data.trainData.Train;

public class Order {

    private final Train train;
    private final Station start;
    private final Station end;
    private final Ticket ticket;
    private int num;
    private final double per_cost;
    private double cost;

    public double getCost() {
        return cost;
    }

    private boolean paid;

    public Order(Train train, Station start, Station end, Ticket ticket, int num) {
        this.train = train;
        this.start = start;
        this.end = end;
        this.ticket = ticket;
        this.num = num;
        per_cost = ticket.getPrice() * Math.abs(start.getMiles() - end.getMiles());
        updateCost();
    }

    @Override
    public String toString() {
        return "[" + train.getTrainId() + ": " + start.getName() + "->" +
                end.getName() + "] seat:" + ticket.getSeat() + " num:" +
                num + " price:" + String.format("%.2f", cost) + " paid:" +
                (paid ? "T" : "F");
    }

    public boolean check(String train, String start, String end, String seat) {
        return this.train.getTrainId().equals(train) &&
                this.start.getName().equals(start) &&
                this.end.getName().equals(end) &&
                this.ticket.getSeat().equals(seat);
    }

    public void delete(int num) {
        this.num -= num;
        ticket.addQuantity(num);
        updateCost();
    }

    public boolean getPaid() {
        return paid;
    }

    public void updateCost() {
        this.cost = per_cost * num;
    }

    public int genNum() {
        return num;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public double getPer_cost() {
        return per_cost;
    }
}
