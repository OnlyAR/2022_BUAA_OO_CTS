package Data.trainData;

import exception.*;

public class Gatimaan extends Train {

    public Gatimaan(String trainId, String lineId, String[] ticketInfo) throws TicketNumIllegalException,
            ArgsIllegalException, PriceIllegalException, UnknownException {
        super(trainId, lineId, ticketInfo, "SC", "HC", "SB");
    }
}
