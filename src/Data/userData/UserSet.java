package Data.userData;

import exception.*;

import java.util.HashMap;

public class UserSet {

    private final HashMap<String, User> userSet;

    public UserSet() {
        userSet = new HashMap<>();
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
            User user = new User(args[0], args[1], args[2]);
            if (userSet.containsKey(user.getAadhaarNum()))
                throw new AadhaarNumExistException();
            userSet.put(user.getAadhaarNum(), user);
            System.out.println(user);
        }  catch (ArgsIllegalException e) {
            throw e;
        } catch (NameIllegalException e) {
            System.out.println("Name illegal");
        } catch (SexIllegalException e) {
            System.out.println("Sex illegal");
        } catch (AadhaarNumIllegalException e) {
            System.out.println("Aadhaar number illegal");
        } catch (AadhaarNumExistException e) {
            System.out.println("Aadhaar number exist");
        }
    }

}
