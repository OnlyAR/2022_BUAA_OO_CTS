package Data;

public class Data {

    /**
     * 判断一个字符串是否是整数(允许前导零)
     *
     * @param num 待判断的字符串
     * @return 判断结果
     */
    public static boolean isDigit(String num) {
        for (int i = 0; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * 删去一个字符串数组的首个元素
     *
     * @param strings 字符串数组
     * @return 去掉 strings[0] 的字符串数组
     */
    public static String[] delFirst(String[] strings) {
        String[] temp = new String[strings.length - 1];
        System.arraycopy(strings, 1, temp, 0, temp.length);
        return temp;
    }

}
