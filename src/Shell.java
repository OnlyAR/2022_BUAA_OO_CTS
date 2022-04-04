import Data.Manager.*;
import Data.lineData.LineMap;
import Data.userData.UserMap;
import exception.ArgsIllegalException;
import exception.CmdNonExistException;

import java.util.Scanner;

import static Data.Data.delFirst;

public class Shell {

    private Manager manager;
    private final UserMap userMap;
    private final LineMap lineMap;

    public Shell() {
        manager = new PrimeManager();
        userMap = new UserMap();
        lineMap = new LineMap();
    }

    void enterSuperManager() {
        manager = new SuperManager();
        System.out.println("DuluDulu");
    }

    void exitSuperManager() {
        manager = new PrimeManager();
        System.out.println("DaDaDa");
    }

    void alreadyInMode() {
        System.out.println("WanNiBa");
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        String cmdLine;
        while (true) {
            cmdLine = in.nextLine();

            if (cmdLine.equals("QUIT")) {
                System.out.println("----- Good Bye! -----");
                break;
            }

            String[] args = cmdLine.split(" ");

            try {
                if (!manager.containsCmd(args[0]))
                    throw new CmdNonExistException();
                switch (args[0]) {
                    case "TunakTunakTun":
                        if (manager instanceof PrimeManager) enterSuperManager();
                        else alreadyInMode(); break;
                    case "NutKanutKanut":
                        if (manager instanceof SuperManager) exitSuperManager();
                        else alreadyInMode(); break;
                    case "addUser":     userMap.addUser(delFirst(args));     break;
                    case "addLine":     lineMap.addLine(delFirst(args));     break;
                    case "delLine":     lineMap.delLine(delFirst(args));     break;
                    case "addStation":  lineMap.addStation(delFirst(args));  break;
                    case "delStation":  lineMap.delStation(delFirst(args));  break;
                    case "lineInfo":    lineMap.lineInfo(delFirst(args));    break;
                    case "listLine":    lineMap.listLine(delFirst(args));    break;
                    case "addTrain":    lineMap.addTrain(delFirst(args));    break;
                    case "delTrain":    lineMap.delTrain(delFirst(args));    break;
                    case "checkTicket": lineMap.checkTicket(delFirst(args)); break;
                    case "listTrain":   lineMap.listTrain(delFirst(args));   break;
                    default:            throw new CmdNonExistException();
                }
            } catch (CmdNonExistException e) { System.out.println("Command does not exist"); }
              catch (ArgsIllegalException e) { System.out.println("Arguments illegal"); }
              catch (Exception e)            { System.out.println("Unknown error"); }
        }
        in.close();
    }

}
