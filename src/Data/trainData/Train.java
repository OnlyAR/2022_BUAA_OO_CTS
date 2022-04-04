package Data.trainData;

import exception.ArgsIllegalException;
import exception.PriceIllegalException;
import exception.TicketNumIllegalException;
import exception.UnknownException;

import java.util.ArrayList;
import java.util.Collections;

public class Train {

    private final String trainId;
    private final String lineId;
    private final ArrayList<Ticket> tickets;
    private final ArrayList<String> seatType;

    public String getLineId() {
        return lineId;
    }

    public Train(String trainId, String lineId, String[] ticketInfo, String... seatType)
            throws TicketNumIllegalException, ArgsIllegalException, PriceIllegalException, UnknownException {
        try {
            this.trainId = trainId;
            this.lineId  = lineId;
            tickets = new ArrayList<>();
            for (int i = 0; i < ticketInfo.length; i += 2)
                tickets.add(new Ticket(seatType[i / 2], ticketInfo[i], ticketInfo[i + 1]));
            this.seatType = new ArrayList<>();
            Collections.addAll(this.seatType, seatType);
        } catch (NullPointerException e) {
            throw new UnknownException();
        }
    }

    public String getTrainId() {
        return trainId;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public boolean containSeat(String seat) {
        return seatType.contains(seat);
    }

    public String showTickets() {
        StringBuilder print = new StringBuilder();
        for (Ticket ticket : tickets)
            print.append(ticket);
        return print.toString();
    }

    public static int trainTypeSort(char type) {
        switch (type) {
            case 'K': return 0;
            case 'G': return 1;
            case '0': return 2;
            default:  return -1;
        }
    }

}