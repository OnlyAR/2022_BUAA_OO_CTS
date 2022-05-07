package Data.userData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CertTable {

    public HashMap<String, Boolean> certMap = new HashMap<>();

    public void importCert(String filename) {
        int positive = 0;
        int negative = 0;
        try {
            HashMap<String, Boolean> tempMap = read(filename);
            for (String key: tempMap.keySet()) {
                certMap.put(key, tempMap.get(key));
            }
            for (String key: certMap.keySet()) {
                if (certMap.get(key))
                    positive++;
                else negative++;
            }
            System.out.println("Import Success, Positive:" + positive + " Negative:" + negative);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Boolean> read(String fileName) throws IOException {
        HashMap<String, Boolean> cert = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lines = line.split(",");
            if (lines[1].equals("P")) {
                cert.put(lines[0], true);
            } else {
                cert.put(lines[0], false);
            }
        }
        br.close();
        return cert;
    }
}
