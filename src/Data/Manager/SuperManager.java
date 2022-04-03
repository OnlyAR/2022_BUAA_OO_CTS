package Data.Manager;

public class SuperManager extends Manager {
    public SuperManager() {
        super();
        addCmd("addLine", "delLine", "addStation", "delStation", "addTrain", "delTrain");
    }
}
