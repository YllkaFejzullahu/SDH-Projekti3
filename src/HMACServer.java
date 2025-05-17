import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HMACServer {

    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            String secretKey = ConfigLoader.loadSecretKey();

            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                System.out.println("Server started and awaiting messages...");

                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                         BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

                        String received = in.readUTF();
                        String[] parts = received.split("::");

                        if (parts.length != 2) {
                            out.write("Invalid message format.\n");
                            out.flush();
                            continue;
                        }

                        String message = parts[0];
                        String receivedHMAC = parts[1];

                        System.out.println("Message received with HMAC: [" + message + " | " + receivedHMAC + "]");
                        System.out.println("Validating HMAC...");

                        String calculatedHMAC = generateHMAC(message, secretKey);

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

    // Prevents timing attacks by comparing HMACs in constant time
    private static boolean timingSafeEqual(String a, String b) {
        if (a.length() != b.length()) return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}
