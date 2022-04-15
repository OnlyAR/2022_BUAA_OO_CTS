package Data.lineData;

import Data.trainData.*;
import exception.*;

import java.util.ArrayList;
import java.util.Comparator;

public class Line {

    private final String lineId;
    private final int capacity;
    private final ArrayList<Station> stations;
    private final ArrayList<Train> trains;

    public Line(String lineId, int capacity, String[] stationName, int[] stationMiles)
            throws StationDuplicateException {
        this.lineId = lineId;
        this.capacity = capacity;
        stations = new ArrayList<>();
        for (int i = 0; i < stationName.length; i++) {
            if (containStation(stationName[i]))
                throw new StationDuplicateException();
            stations.add(new Station(stationName[i], stationMiles[i]));
        }
        trains = new ArrayList<>();
    }

    public void addStation(String stationName, int stationMiles) throws StationDuplicateException {
        if (containStation(stationName))
            throw new StationDuplicateException();
        stations.add(new Station(stationName, stationMiles));
    }

    private boolean containStation(String name) {
        for (Station s : stations)
            if (s.getName().equals(name))
                return true;
        return false;
    }

    public void delStation(String stationName) throws StationNonExistException {
        if (!containStation(stationName))
            throw new StationNonExistException();
        stations.removeIf(s -> s.getName().equals(stationName));
    }

    public String getLineId() {
        return lineId;
    }

    @Override
    public String toString() {
        Comparator<? super Station> comparator = Comparator.comparingInt(Station::getMiles);
        stations.sort(comparator);
        StringBuilder print = new StringBuilder(lineId + " " + trains.size() + "/" + capacity);
        for (Station s : stations)
            print.append(" ").append(s.getName()).append(":").append(s.getMiles());
        return print.toString();
    }

    public boolean containsTrain(String trainId) {
        for (Train train : trains)
            if (train.getTrainId().equals(trainId))
                return true;
        return false;
    }

    public boolean full() {
        return trains.size() == capacity;
    }

    public void addTrain(String trainType, String trainId, String[] args) throws TicketNumIllegalException,
            ArgsIllegalException, PriceIllegalException, UnknownException {
        switch (trainType) {
            case "Normal":   trains.add(new Normal(trainId, lineId, args));   break;
            case "Gatimaan": trains.add(new Gatimaan(trainId, lineId, args)); break;
            case "Koya":     trains.add(new Koya(trainId, lineId, args));     break;
        }
    }

    public void delTrain(String trainId) {
        trains.removeIf(t -> t.getTrainId().equals(trainId));
    }

    public void checkTicket(String trainId, String start, String end, String seat)
            throws StationNonExistException, SeatUnmachedException {
        int startMiles = getMiles(start);
        int endMiles = getMiles(end);
        int distance = Math.abs(startMiles - endMiles);
        double price;
        for (Train train : trains)
            if (train.getTrainId().equals(trainId)) {
                if (!train.containSeat(seat))
                    throw new SeatUnmachedException();
                for (Ticket ticket : train.getTickets())
                    if (ticket.getSeat().equals(seat)) {
                        price = ticket.getPrice() * distance;
                        System.out.println("[" + trainId + ": " + start + "->" + end + "] seat:" + seat +
                                " remain:" + ticket.getQuantity() + " distance:" + distance +
                                " price:" + String.format("%.2f", price));
                        return;
                    }
            }
    }

    private int getMiles(String stationName) throws StationNonExistException {
        for (Station s : stations)
            if (s.getName().equals(stationName))
                return s.getMiles();
        throw new StationNonExistException();
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }

    public Train getTrain(String trainId) {
        for (Train train: trains)
            if (train.getTrainId().equals(trainId))
                return train;
        return null;
    }

    public boolean containsStation (String stationName) {
        for (Station station: stations) {
            if (station.getName().equals(stationName))
                return true;
        }
        return false;
    }

    public Station getStation(String stationName) {
        for (Station station: stations)
            if (station.getName().equals(stationName))
                return station;
        return null;
    }

}
