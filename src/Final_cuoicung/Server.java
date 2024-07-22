/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_cuoicung;

/**
 *
 * @author Admin
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Base64;


public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started");
            RMIServer.main(new String[]{});

            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String base64SignedMessage = in.readLine();
                String message = in.readLine();

                // Giải mã Base64
                String signedMessage = new String(Base64.getDecoder().decode(base64SignedMessage));
                PublicKey publicKey = KeyUtils.getPublicKey("public.pem");

                // Xác thực chữ ký
                boolean isVerified = RSAUtils.verify(message, signedMessage, publicKey);
                if (isVerified) {
                    out.println("VERIFIED;HUFLIT");
                } else {
                    out.println("NOT VERIFIED");
                }

                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

