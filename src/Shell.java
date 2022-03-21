import data.UserSet;

import java.util.Scanner;

public class Shell {

    /**
     *  一个存放 User 的容器类
     */
    private final UserSet userSet;

    public Shell() {
        userSet = new UserSet();
    }

    /**
     * 删去一个字符串数组的首个元素
     *
     * @param strings 字符串数组
     * @return 去掉 strings[0] 的字符串数组
     */
    private String[] delFirst(String[] strings) {
        String[] temp = new String[strings.length - 1];
        System.arraycopy(strings, 1, temp, 0, temp.length);
        return temp;
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
            switch (args[0]) {
                case "addUser":
                    userSet.addUser(delFirst(args));
                    break;
                default:
                    break;
            }
        }
        in.close();
    }

}
