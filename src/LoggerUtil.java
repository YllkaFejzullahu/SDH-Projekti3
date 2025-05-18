import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void log(String message) {
        System.out.println(formatterNow() + " [INFO] " + message);
    }

    public static void logError(String message, Exception e) {
        System.err.println(formatterNow() + " [ERROR] " + message + (e != null ? ": " + e.getMessage() : ""));
    }

    private static String formatterNow() {
        return LocalDateTime.now().format(formatter);
    }
}
