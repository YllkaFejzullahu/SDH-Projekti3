import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static String loadSecretKey() {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Skedari config.properties nuk u gjet!");
            }
            prop.load(input);
            return prop.getProperty("secretKey");
        } catch (Exception ex) {
            throw new RuntimeException("Gabim gjate ngarkimit te çelësit sekret", ex);
        }
    }


    public static void main(String[] args) {
        String secret = loadSecretKey();
        System.out.println("Çelësi i ngarkuar: " + secret); //celesii
    }
}


