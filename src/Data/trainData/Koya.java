package Data.trainData;

import exception.*;

public class Koya extends Train {

    public Koya(String trainId, String lineId, String[] ticketInfo) throws TicketNumIllegalException,
            ArgsIllegalException, PriceIllegalException, UnknownException {
        super(trainId, lineId, ticketInfo, "1A", "2A");
    }
}
