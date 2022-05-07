package Data.manager;

import java.util.ArrayList;
import java.util.Collections;

public class Manager {
    private final ArrayList<String> cmdList;

    public Manager() {
        cmdList = new ArrayList<>();
        Collections.addAll(cmdList, "addUser", "TunakTunakTun",
                "NutKanutKanut", "listTrain", "lineInfo", "listLine", "login",
                "logout", "buyTicket", "listOrder", "rechargeBalance", "checkBalance",
                "cancelOrder", "payOrder");
    }

    public void addCmd(String... cmd) {
        Collections.addAll(cmdList, cmd);
    }

    public boolean containsCmd(String cmd) {
        return cmdList.contains(cmd);
    }
}
