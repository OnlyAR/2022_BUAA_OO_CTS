package Data.trainData;

import exception.*;

public class Ticket {

    private final String seat;
    private double price;
    private int quantity;

    public Ticket(String seat, String price, String quantity) throws PriceIllegalException,
            ArgsIllegalException, TicketNumIllegalException {
        this.seat = seat;
        setPrice(price);
        setQuantity(quantity);
    }

    public void setPrice(String str) throws PriceIllegalException, ArgsIllegalException {
        try {
            double price = Double.parseDouble(str);
            if (price <= 0)
                throw new PriceIllegalException();
            this.price = price;
        } catch (NumberFormatException e) {
            throw new ArgsIllegalException();
        }
    }

    public void setQuantity(String str) throws ArgsIllegalException, TicketNumIllegalException {
        try {
            int num = Integer.parseInt(str);
            if (num < 0)
                throw new TicketNumIllegalException();
            this.quantity = num;
        } catch (NumberFormatException e) {
            throw new ArgsIllegalException();
        }
    }

    public double getPrice() {
        return price;
    }

    public String getSeat() {
        return seat;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return " [" + seat + "]" + String.format("%.2f", price) + ":" + quantity;
    }
}
