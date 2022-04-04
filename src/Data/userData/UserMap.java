package Data.userData;

import exception.*;

import java.util.HashMap;

public class UserMap {

    private final HashMap<String, User> users;

    public UserMap() {
        users = new HashMap<>();
    }

    /**
     * 接收命令参数，保存进容器，并进行异常处理
     *
     * @param args name, sex, Aadhaar number 三个参数
     */
    public void addUser(String[] args) throws ArgsIllegalException {
        try {
            if (args.length != 3)
                throw new ArgsIllegalException();
            String userName = args[0];
            String userSex = args[1];
            String aadhaarNum = args[2];
            User user = new User(userName, userSex, aadhaarNum);
            if (users.containsKey(user.getAadhaarNum()))
                throw new AadhaarNumExistException();
            users.put(user.getAadhaarNum(), user);
            System.out.println(user);
        } catch (NameIllegalException e)       { System.out.println("Name illegal");}
          catch (SexIllegalException e)        { System.out.println("Sex illegal"); }
          catch (AadhaarNumIllegalException e) { System.out.println("Aadhaar number illegal"); }
          catch (AadhaarNumExistException e)   { System.out.println("Aadhaar number exist"); }
    }

}
