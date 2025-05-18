import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.Duration;

public class HMACServer {

    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        char[] secretKeyChars = null;
        try {
            secretKeyChars = ConfigLoader.loadSecretKey();


            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                System.out.println("Server started and awaiting messages...");

                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                         BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

                        String received = in.readUTF();
                        String[] parts = received.split("::");

                        if (parts.length != 3) {
                            out.write("Invalid message format.\n");
                            out.flush();
                            continue;
                        }
                        String timestamp = parts[0];
                        String message = parts[1];
                        String receivedHMAC = parts[2];

                        if (!isTimestampValid(timestamp)) {
                            System.out.println("Message rejected due to invalid or expired timestamp.");
                            out.write("Message rejected: invalid or expired timestamp.\n");
                            out.flush();
                            continue;
                        }


                        System.out.println("Message received with HMAC: [" + timestamp + " | " + message + " | " + receivedHMAC + "]");
                        System.out.println("Validating HMAC...");

                        String messageToVerify = timestamp + "::" + message;
                        String calculatedHMAC = generateHMAC(messageToVerify, secretKeyChars);



                        if (timingSafeEqual(receivedHMAC, calculatedHMAC)) {
                            System.out.println("Message verified successfully. Integrity and authenticity confirmed.");
                            out.write("Message verified successfully.\n");
                        } else {
                            System.out.println("HMAC verification failed. Message integrity compromised.");
                            out.write("HMAC verification failed.\n");
                        }

                        out.flush();
                    } catch (IOException e) {
                        System.err.println("Error handling client connection: " + e.getMessage());
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (secretKeyChars != null) {
                java.util.Arrays.fill(secretKeyChars, '\0');
                System.out.println("Secret key cleared from memory.");
            }
        }
    }

    private static String generateHMAC(String message, char[] secretChars) throws Exception {
        byte[] secretBytes = charArrayToUtf8Bytes(secretChars);
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
            java.util.Arrays.fill(secretBytes, (byte) 0);  // Pastrim bytes nga memoria
        }
    }


    private static byte[] charArrayToUtf8Bytes(char[] chars) {
        return new String(chars).getBytes(StandardCharsets.UTF_8);
    }


    private static boolean timingSafeEqual(String a, String b) {
        if (a.length() != b.length()) return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }

    private static boolean isTimestampValid(String timestamp) {
        try {
            Instant messageTime = Instant.parse(timestamp);
            Instant now = Instant.now();
            Duration diff = Duration.between(messageTime, now);
            long seconds = diff.getSeconds();
            return seconds >= 0 && seconds <= 10;
        } catch (Exception e) {
            return false;
        }
    }

}
