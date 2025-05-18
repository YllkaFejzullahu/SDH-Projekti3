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
            LoggerUtil.log("Secret key successfully loaded by client.");


            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your message: ");
            String message = scanner.nextLine();

            String timestamp = Instant.now().toString();
            String messageToSend = timestamp + "::" + message;
            String hmac = generateHMAC(messageToSend, secretKeyChars);
            LoggerUtil.log("Generated HMAC: " + hmac);

            try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                LoggerUtil.log("Connecting to server at " + SERVER_HOST + ":" + SERVER_PORT);
                String combined = timestamp + "::" + message + "::" + hmac;
                LoggerUtil.log("Sending message with HMAC: [" + combined + "]");

                out.writeUTF(combined);
                LoggerUtil.log("Message with timestamp and HMAC sent to server.");

                String response = in.readLine();
                LoggerUtil.log("Server response: " + response);

            }
            java.util.Arrays.fill(secretKeyChars, '\0');

        } catch (Exception e) {
            LoggerUtil.logError("An error occurred in the client", e);
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

