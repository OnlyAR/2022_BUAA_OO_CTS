package Data.userData;

import exception.AadhaarNumIllegalException;
import exception.NameIllegalException;
import exception.SexIllegalException;

public class User {

    private String name;
    private char sex;
    private String aadhaarNum;

    public User(String name, String sex, String aadhaarNum)
            throws NameIllegalException, SexIllegalException, AadhaarNumIllegalException {
        setName(name);
        setSex(sex);
        setAadhaarNum(aadhaarNum);
    }

    public void setName(String name) throws NameIllegalException {
        if (!nameLegal(name))
            throw new NameIllegalException();
        this.name = name;
    }

    public void setSex(String sex) throws SexIllegalException {
        if (!sexLegal(sex))
            throw new SexIllegalException();
        this.sex = sex.charAt(0);
    }

    public void setAadhaarNum(String aadhaarNum) throws AadhaarNumIllegalException {
        if (!AadhaarNum.legal(aadhaarNum))
            throw new AadhaarNumIllegalException();
        int sexCode = aadhaarNum.charAt(aadhaarNum.length() - 1) - '0';
        if (!(sexCode == 0 && sex == 'F' || sexCode == 1 && sex == 'M' || sexCode == 2 && sex == 'O'))
            throw new AadhaarNumIllegalException();
        this.aadhaarNum = aadhaarNum;
    }

    boolean nameLegal(String name) {
        for (int i = 0; i < name.length(); i++)
            if (!(Character.isAlphabetic(name.charAt(i)) || name.charAt(i) == '_'))
                return false;
        return true;
    }

    boolean sexLegal(String sex) {
        return sex.equals("F") || sex.equals("M") || sex.equals("O");
    }

    public String getAadhaarNum() {
        return aadhaarNum;
    }

    @Override
    public String toString() {
        return "Name:"      + name +
               "\nSex:"     + sex +
               "\nAadhaar:" + aadhaarNum;
    }
}
