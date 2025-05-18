
import java.io.InputStream;
import java.security.Key;
import java.util.Properties;

public class ConfigLoader {
    public static char[] loadSecretKey() {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Skedari config.properties nuk u gjet!");
            }
            prop.load(input);

            String key = prop.getProperty("secretKey");
            if (key == null) {
                throw new RuntimeException("Çelësi sekret nuk u gjet në config.properties!");
            }
            char[] keyChars = key.toCharArray(); // më i sigurt se String
            key = null; // fshijmë referencën për të lejuar GC
            return keyChars;
        } catch (Exception ex) {
            throw new RuntimeException("Gabim gjate ngarkimit te çelësit sekret", ex);
        }

    }


    public static void main(String[] args) {
        char[] secret = loadSecretKey();
        System.out.println("Çelësi i ngarkuar: " + new String(secret));
        //pastrimi i qelesit nga memoria pas perdorimit
        java.util.Arrays.fill(secret, '\0');
    }
}

