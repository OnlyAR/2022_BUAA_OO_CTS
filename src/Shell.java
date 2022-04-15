import Data.Manager.*;
import Data.lineData.LineMap;
import Data.userData.User;
import Data.userData.UserMap;
import exception.*;

import java.util.Scanner;

import static Data.Data.delFirst;

public class Shell {

    private Manager manager;
    private User user = null;
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
                        else alreadyInMode();
                        break;
                    case "NutKanutKanut":
                        if (manager instanceof SuperManager) exitSuperManager();
                        else alreadyInMode();
                        break;
                    case "addUser":
                        userMap.addUser(delFirst(args));
                        break;
                    case "addLine":
                        lineMap.addLine(delFirst(args));
                        break;
                    case "delLine":
                        lineMap.delLine(delFirst(args));
                        break;
                    case "addStation":
                        lineMap.addStation(delFirst(args));
                        break;
                    case "delStation":
                        lineMap.delStation(delFirst(args));
                        break;
                    case "lineInfo":
                        lineMap.lineInfo(delFirst(args));
                        break;
                    case "listLine":
                        lineMap.listLine(delFirst(args));
                        break;
                    case "addTrain":
                        lineMap.addTrain(delFirst(args));
                        break;
                    case "delTrain":
                        lineMap.delTrain(delFirst(args));
                        break;
                    case "checkTicket":
                        lineMap.checkTicket(delFirst(args));
                        break;
                    case "listTrain":
                        lineMap.listTrain(delFirst(args));
                        break;
                    case "login":
                        if (delFirst(args).length != 2)
                            throw new ArgsIllegalException();
                        if (user != null)
                            throw new UserLoginException();
                        user = userMap.login(delFirst(args));
                        break;
                    case "logout":
                        if (delFirst(args).length != 0)
                            throw new ArgsIllegalException();
                        if (user == null)
                            throw new NoUserException();
                        user = null;
                        System.out.println("Logout success");
                        break;
                    case "buyTicket":
                        if (delFirst(args).length != 5)
                            throw new ArgsIllegalException();
                        if (user == null)
                            throw new PleaseLoginException();
                        user.buyTicket(lineMap, delFirst(args));
                        break;
                    case "listOrder":
                        if (delFirst(args).length != 0)
                            throw new ArgsIllegalException();
                        if (user == null)
                            throw new PleaseLoginException();
                        user.listOrder();
                        break;
                    default:
                        throw new CmdNonExistException();
                }
            } catch (CmdNonExistException e) {
                System.out.println("Command does not exist");
            } catch (ArgsIllegalException e) {
                System.out.println("Arguments illegal");
            } catch (UserLoginException e) {
                System.out.println("You have logged in");
            } catch (NoUserException e) {
                System.out.println("No user has logged in");
            } catch (PleaseLoginException e) {
                System.out.println("Please login first");
            } catch (UnknownException e) {
                e.printStackTrace();
            }
        }
        in.close();
    }

}
