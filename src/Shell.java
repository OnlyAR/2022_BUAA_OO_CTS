import Data.Manager.Manager;
import Data.Manager.PrimeManager;
import Data.Manager.SuperManager;
import Data.lineData.LineMap;
import Data.userData.UserSet;
import exception.ArgsIllegalException;
import exception.CmdNonExistException;

import java.util.Scanner;

import static Data.Data.delFirst;

public class Shell {

    /**
     * 一个存放 User 的容器类
     */
    private final UserSet userSet;
    private Manager manager;
    private final LineMap lines;

    public Shell() {
        userSet = new UserSet();
        manager = new PrimeManager();
        lines = new LineMap();
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
        String cmd;
        while (true) {
            cmd = in.nextLine();

            // 输入指令 "QUIT" 时退出程序
            if (cmd.equals("QUIT")) {
                System.out.println("----- Good Bye! -----");
                break;
            }

            String[] args = cmd.split(" ");

            try {
                if (manager.containsCmd(args[0]))
                    switch (args[0]) {
                        case "TunakTunakTun":
                            if (manager instanceof PrimeManager) enterSuperManager();
                            else alreadyInMode(); break;
                        case "NutKanutKanut":
                            if (manager instanceof SuperManager) exitSuperManager();
                            else alreadyInMode(); break;
                        case "addUser":     userSet.addUser(delFirst(args));   break;
                        case "addLine":     lines.addLine(delFirst(args));     break;
                        case "delLine":     lines.delLine(delFirst(args));     break;
                        case "addStation":  lines.addStation(delFirst(args));  break;
                        case "delStation":  lines.delStation(delFirst(args));  break;
                        case "lineInfo":    lines.lineInfo(delFirst(args));    break;
                        case "listLine":    lines.listLine(delFirst(args));    break;
                        case "addTrain":    lines.addTrain(delFirst(args));    break;
                        case "delTrain":    lines.delTrain(delFirst(args));    break;
                        case "checkTicket": lines.checkTicket(delFirst(args)); break;
                        case "listTrain":   lines.listTrain(delFirst(args));   break;
                        default: throw new CmdNonExistException();
                    }
                else throw new CmdNonExistException();
            } catch (CmdNonExistException e) {
                System.out.println("Command does not exist");
            } catch (ArgsIllegalException e) {
                System.out.println("Arguments illegal");
            } catch (Exception e) {
                System.out.println("Unknown error");
            }
        }
        in.close();
    }

}
