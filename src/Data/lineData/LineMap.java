package Data.lineData;

import Data.trainData.Train;
import exception.*;

import java.util.*;

import static Data.Data.delFirst;
import static Data.Data.isDigit;

public class LineMap {

    private final Map<String, Line> lines;

    public LineMap() {
        this.lines = new HashMap<>();
    }

    public void addLine(String[] args) throws ArgsIllegalException {
        try {
            if (args.length <= 2 || args.length % 2 != 0)
                throw new ArgsIllegalException();
            String lineId = args[0];
            if (lines.containsKey(lineId))
                throw new LineExistException();
            int capacity = Integer.parseInt(args[1]);
            if (capacity <= 0)
                throw new CapacityIllegalException();
            String[] stationName = new String[args.length / 2 - 1];

            int[] stationMiles = new int[args.length / 2 - 1];
            for (int i = 2; i < args.length; i += 2) {
                stationName[i / 2 - 1] = args[i];
                stationMiles[i / 2 - 1] = Integer.parseInt(args[i + 1]);
                if (stationMiles[i / 2 - 1] < 0)
                    throw new ArgsIllegalException();
            }
            lines.put(lineId, new Line(lineId, capacity, stationName, stationMiles));
            System.out.println("Add Line success");
        } catch (StationDuplicateException e) { System.out.println("Station duplicate"); }
          catch (LineExistException e)        { System.out.println("Line already exists"); }
          catch (CapacityIllegalException e)  { System.out.println("Capacity illegal"); }
          catch (NumberFormatException e)     { throw new ArgsIllegalException(); }
    }

    public void delLine(String[] args) throws ArgsIllegalException {
        try {
            if (args.length != 1)
                throw new ArgsIllegalException();
            String lineId = args[0];
            if (!lines.containsKey(lineId))
                throw new LineNonExistException();
            lines.remove(lineId);
            System.out.println("Del Line success");
        } catch (LineNonExistException e) { System.out.println("Line does not exist"); }
    }

    public void addStation(String[] args) throws ArgsIllegalException {
        try {
            if (args.length != 3 || !isDigit(args[2]))
                throw new ArgsIllegalException();
            String lineId = args[0];
            if (!lines.containsKey(lineId))
                throw new LineNonExistException();
            String stationName = args[1];
            int stationMiles = Integer.parseInt(args[2]);
            lines.get(lineId).addStation(stationName, stationMiles);
            System.out.println("Add Station success");
        } catch (LineNonExistException e)     { System.out.println("Line does not exist"); }
          catch (StationDuplicateException e) { System.out.println("Station duplicate"); }
          catch (NumberFormatException e)     { throw new ArgsIllegalException(); }
    }

    public void delStation(String[] args) throws ArgsIllegalException {
        try {
            if (args.length != 2)
                throw new ArgsIllegalException();
            String lineId = args[0];
            String stationName = args[1];
            if (!lines.containsKey(lineId))
                throw new LineNonExistException();
            lines.get(lineId).delStation(stationName);
            System.out.println("Delete Station success");
        } catch (LineNonExistException e)    { System.out.println("Line does not exist"); }
          catch (StationNonExistException e) { System.out.println("Station does not exist"); }

    }

    public void lineInfo(String[] args) throws ArgsIllegalException {
        try {
            if (args.length != 1)
                throw new ArgsIllegalException();
            String lineId = args[0];
            if (!lines.containsKey(lineId))
                throw new LineNonExistException();
            System.out.println(lines.get(lineId));
        } catch (LineNonExistException e) {
            System.out.println("Line does not exist");
        }
    }

    public void listLine(String[] args) throws ArgsIllegalException {
        if (args.length != 0)
            throw new ArgsIllegalException();
        if (lines.isEmpty()) {
            System.out.println("No Lines");
            return;
        }
        List<Line> list = new ArrayList<>(lines.values());
        Comparator<? super Line> comparator = Comparator.comparing(Line::getLineId);
        list.sort(comparator);
        int idx = 1;
        for (Line l : list) {
            System.out.println("[" + idx + "] " + l);
            idx++;
        }
    }

    private String getTrainType(String trainId) {
        if (trainId.length() != 5)
            return null;
        for (int i = 1; i < 5; i++)
            if (!Character.isDigit(trainId.charAt(i)))
                return null;
        switch (trainId.charAt(0)) {
            case '0': return "Normal";
            case 'G': return "Gatimaan";
            case 'K': return "Koya";
            default:  return null;
        }
    }

    public void addTrain(String[] args) throws ArgsIllegalException, UnknownException {
        try {
            String trainId = args[0];
            String trainType = getTrainType(trainId);
            if (trainType == null)
                throw new TrainSerialIllegalException();

            if ((trainType.equals("Normal")   && args.length != 8) ||
                (trainType.equals("Gatimaan") && args.length != 8) ||
                (trainType.equals("Koya")     && args.length != 6))
                throw new ArgsIllegalException();

            if (containsTrain(trainId))
                throw new TrainSerialDuplicateException();

            String lineId = args[1];
            if (!lines.containsKey(lineId) || lines.get(lineId).full())
                throw new LineIllegalException();
            lines.get(lineId).addTrain(trainType, trainId, delFirst(delFirst(args)));
            System.out.println("Add Train Success");
        } catch (TrainSerialIllegalException e)   { System.out.println("Train serial illegal"); }
          catch (TrainSerialDuplicateException e) { System.out.println("Train serial duplicate"); }
          catch (LineIllegalException e)          { System.out.println("Line illegal"); }
          catch (TicketNumIllegalException e)     { System.out.println("Ticket num illegal"); }
          catch (PriceIllegalException e)         { System.out.println("Price illegal"); }
    }

    public boolean containsTrain(String trainId) {
        for (String k : lines.keySet())
            if (lines.get(k).containsTrain(trainId))
                return true;
        return false;
    }

    public void delTrain(String[] args) throws ArgsIllegalException {
        try {
            if (args.length != 1)
                throw new ArgsIllegalException();
            String trainId = args[0];
            boolean containsTrain = false;
            for (String k : lines.keySet())
                if (lines.get(k).containsTrain(trainId)) {
                    containsTrain = true;
                    lines.get(k).delTrain(trainId);
                    break;
                }
            if (!containsTrain)
                throw new TrainNonExistException();
            System.out.println("Del Train Success");
        } catch (TrainNonExistException e) {
            System.out.println("Train does not exist");
        }
    }

    public void checkTicket(String[] args) throws ArgsIllegalException {
        try {
            if (args.length != 4)
                throw new ArgsIllegalException();
            String trainId = args[0];
            String start = args[1];
            String end = args[2];
            String seat = args[3];
            String trainType = getTrainType(trainId);
            if (trainType == null)
                throw new TrainSerialIllegalException();
            boolean containsTrain = false;
            for (String lineId : lines.keySet()) {
                if (lines.get(lineId).containsTrain(trainId)) {
                    containsTrain = true;
                    lines.get(lineId).checkTicket(trainId, start, end, seat);
                    break;
                }
            }
            if (!containsTrain)
                throw new TrainNonExistException();
        } catch (TrainSerialIllegalException e) { System.out.println("Train serial illegal"); }
          catch (TrainNonExistException e)      { System.out.println("Train serial does not exist"); }
          catch (StationNonExistException e)    { System.out.println("Station does not exist"); }
          catch (SeatUnmachedException e)       { System.out.println("Seat does not match"); }
    }

    public void listTrain(String[] args) throws ArgsIllegalException {
        try {
            if (args.length > 1)
                throw new ArgsIllegalException();
            ArrayList<Train> trains = new ArrayList<>();
            if (args.length == 1) {
                String lineId = args[0];
                if (!lines.containsKey(lineId))
                    throw new LineNonExistException();
                Line line = lines.get(lineId);
                trains = line.getTrains();
            } else {
                Line line;
                if (lines.isEmpty())
                    throw new LineNonExistException();
                for (String lineId : lines.keySet()) {
                    line = lines.get(lineId);
                    trains.addAll(line.getTrains());
                }
            }
            printTrain(trains);
        } catch (LineNonExistException e) { System.out.println("Line does not exist"); }
    }

    private void printTrain(ArrayList<Train> trains) {
        if (trains.isEmpty())
            System.out.println("No Trains");

        Comparator<? super Train> comparator = (Comparator<Train>) (o1, o2) -> {
            int type1 = Train.trainTypeSort(o1.getTrainId().charAt(0));
            int type2 = Train.trainTypeSort(o2.getTrainId().charAt(0));
            if (type1 != type2)
                return type1 - type2;
            String code1 = o1.getTrainId().substring(1);
            String code2 = o2.getTrainId().substring(1);
            return code1.compareTo(code2);
        };
        trains.sort(comparator);

        int idx = 1;
        for (Train train : trains) {
            System.out.println("[" + idx + "] " + train.getTrainId() + ": "
                               + train.getLineId() + train.showTickets());
            idx++;
        }
    }

    public Line getLine(String trainID) {
        Line line;
        for (String k: lines.keySet()) {
            line = lines.get(k);
            if (line.containsTrain(trainID))
                return line;
        }
        return null;
    }
}
