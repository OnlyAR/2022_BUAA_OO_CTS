package data;

public class AadhaarNum {

    public static boolean legal(String aadhaarNum) {
        if (!isDigit(aadhaarNum))
            return false;
        if (aadhaarNum.length() != 12)
            return false;
        String districtCode = getDistrict(aadhaarNum);
        String casteCode = getCaste(aadhaarNum);
        String bioCode = getBio(aadhaarNum);
        if (Integer.parseInt(districtCode) < 1 || Integer.parseInt(districtCode) > 1237)
            return false;
        if (Integer.parseInt(casteCode) < 20 ||
                Integer.parseInt(casteCode) > 460)
            return false;
        if (Integer.parseInt(bioCode.substring(0, 3)) > 100)
            return false;
        return bioCode.charAt(3) <= '3';
    }

    private static boolean isDigit(String aadhaarNum) {
        for (int i = 0; i < aadhaarNum.length(); i++) {
            if (!Character.isDigit(aadhaarNum.charAt(i)))
                return false;
        }
        return true;
    }

    private static String getBio(String aadhaarNum) {
        return aadhaarNum.substring(8);
    }

    private static String getCaste(String aadhaarNum) {
        return aadhaarNum.substring(4, 8);
    }

    private static String getDistrict(String aadhaarNum) {
        return aadhaarNum.substring(0, 4);
    }

}
