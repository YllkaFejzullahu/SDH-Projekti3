import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Scanner;

public class HMACClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            char[] secretKeyChars = ConfigLoader.loadSecretKey();


            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your message: ");
            String message = scanner.nextLine();

            String timestamp = Instant.now().toString(); // shto timestamp
            String messageToSend = timestamp + "::" + message; // kombinim për HMAC
            String hmac = generateHMAC(messageToSend, secretKeyChars);

            try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String combined = timestamp + "::" + message + "::" + hmac;
                System.out.println("Sending message with HMAC: [" + combined + "]");
                out.writeUTF(combined);

                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
            // Pastrimi i çelësit nga memoria:
            java.util.Arrays.fill(secretKeyChars, '\0');

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateHMAC(String message, char[] secretChars) throws Exception {
        byte[] secretBytes = new String(secretChars).getBytes(StandardCharsets.UTF_8);
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
            mac.init(secretKey);
            byte[] hmacBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hmacBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } finally {
            java.util.Arrays.fill(secretBytes, (byte) 0);
        }
    }

}

