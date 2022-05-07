package Data.userData;

import exception.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class UserMap {

    private final HashMap<String, User> users;
    private final CertTable certTable;

    public UserMap() {
        users = new HashMap<>();
        certTable = new CertTable();
    }

    /**
     * 接收命令参数，保存进容器，并进行异常处理
     *
     * @param args name, sex, Aadhaar number 三个参数
     */
    public void addUser(String[] args) throws ArgsIllegalException {
        try {
            if (args.length != 3 && args.length != 4)
                throw new ArgsIllegalException();
            String userName = args[0];
            String userSex = args[1];
            String aadhaarNum = args[2];
            User user;
            if (args.length == 3)
                user = new User(userName, userSex, aadhaarNum, certTable);
            else {
                int discount_times = Integer.parseInt(args[3]);
                user = new Student(userName, userSex, aadhaarNum,  discount_times, certTable);
            }

            if (users.containsKey(user.getAadhaarNum()))
                throw new AadhaarNumExistException();
            users.put(user.getAadhaarNum(), user);
            System.out.println(user);
        } catch (NameIllegalException e) {
            System.out.println("Name illegal");
        } catch (SexIllegalException e) {
            System.out.println("Sex illegal");
        } catch (AadhaarNumIllegalException e) {
            System.out.println("Aadhaar number illegal");
        } catch (AadhaarNumExistException e) {
            System.out.println("Aadhaar number exist");
        } catch (NumberFormatException e) {
            throw new ArgsIllegalException();
        }
    }

    public User login(String[] args) {
        try {
            String aadhaarNum = args[0];
            String userName = args[1];
            if (!users.containsKey(aadhaarNum))
                throw new UserNonExistException();
            User user = users.get(aadhaarNum);
            if (user.getName().equals(userName)) {
                System.out.println("Login success");
                return user;
            } else
                throw new WrongNameException();
        } catch (UserNonExistException e) {
            System.out.println("User does not exist");
        } catch (WrongNameException e) {
            System.out.println("Wrong name");
        }
        return null;
    }

    public void importCert(String[] args) {
        String filename = args[0];
        certTable.importCert(filename);
    }
}
