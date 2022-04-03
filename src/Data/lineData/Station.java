package Data.lineData;

public class Station{

    private final String name;
    private final int miles;

    public Station(String name, int miles) {
        this.name = name;
        this.miles = miles;
    }

    public int getMiles() {
        return miles;
    }

    public String getName() {
        return name;
    }
}
