package Data.userData;

import Data.lineData.Line;
import Data.lineData.Station;
import Data.trainData.Ticket;
import Data.trainData.Train;

public class Order {

    private final Train train;
    private final Station start;
    private final Station end;
    private final Ticket ticket;
    private final int num;
    private final double cost;

    public Order(Train train, Station start, Station end, Ticket ticket, int num) {
        this.train = train;
        this.start = start;
        this.end = end;
        this.ticket = ticket;
        this.num = num;
        cost = ticket.getPrice() * Math.abs(start.getMiles() - end.getMiles()) * num;
    }

    @Override
    public String toString() {
        return "[" + train.getTrainId() + ": " + start.getName() + "->" +
                end.getName() + "] seat:" + ticket.getSeat() + " num:" +
                num + " price:" + String.format("%.2f", cost);
    }
}
