package QUANLINHANSU.util;

import java.io.*;

public class SessionManager {

    private static final String FILE = "session.txt";

    public static void saveSession(boolean status) {
        try (FileWriter writer = new FileWriter(FILE)) {
            writer.write(String.valueOf(status));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isLoggedIn() {
        try {
            File file = new File(FILE);
            if (!file.exists()) return false;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String status = reader.readLine();
            reader.close();

            return Boolean.parseBoolean(status);
        } catch (Exception e) {
            return false;
        }
    }

    public static void clearSession() {
        saveSession(false);
    }
}