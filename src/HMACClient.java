import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HMACClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            String secretKey = ConfigLoader.loadSecretKey();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your message: ");
            String message = scanner.nextLine();

            String hmac = generateHMAC(message, secretKey);

            try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String combined = message + "::" + hmac;
                System.out.println("Sending message with HMAC: [" + message + " | " + hmac + "]");
                out.writeUTF(combined);

                String response = in.readLine();
                System.out.println("Server response: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateHMAC(String message, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        byte[] hmacBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hmacBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}

