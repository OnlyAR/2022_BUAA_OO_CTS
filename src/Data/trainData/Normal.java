package Data.trainData;

import exception.ArgsIllegalException;
import exception.PriceIllegalException;
import exception.TicketNumIllegalException;
import exception.UnknownException;

public class Normal extends Train {

    public Normal(String trainId, String lineId, String[] ticketInfo) throws TicketNumIllegalException,
            ArgsIllegalException, PriceIllegalException, UnknownException {
        super(trainId, lineId, ticketInfo, "CC", "SB", "GG");
    }
}
