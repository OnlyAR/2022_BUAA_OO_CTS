package Data.Manager;

import java.util.ArrayList;
import java.util.Collections;

public class Manager {
    private final ArrayList<String> cmdList;

    public Manager() {
        cmdList = new ArrayList<>();
        Collections.addAll(cmdList, "addUser", "TunakTunakTun",
                "NutKanutKanut", "listTrain", "lineInfo", "listLine");
    }

    public void addCmd(String... cmd) {
        Collections.addAll(cmdList, cmd);
    }

    public boolean containsCmd(String cmd) {
        return cmdList.contains(cmd);
    }
}
