/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_cuoicung;


import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class KeyUtils {

    public static PrivateKey getPrivateKey(String filename) throws Exception {
        try {
            // Đọc nội dung của file khóa
            String key = new String(Files.readAllBytes(Paths.get(filename)));

            // Loại bỏ header và footer
            key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                     .replace("-----END PRIVATE KEY-----", "")
                     .replaceAll("\\s", ""); // Loại bỏ các khoảng trắng và dòng trống

            // Giải mã chuỗi base64
            byte[] keyBytes = Base64.getDecoder().decode(key);

            // Tạo PKCS8EncodedKeySpec và khóa
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để debug
            throw e; // Ném lại ngoại lệ để xử lý ở nơi gọi
        }
    }

    public static PublicKey getPublicKey(String filename) throws Exception {
        try {
            // Đọc nội dung của file khóa
            String key = new String(Files.readAllBytes(Paths.get(filename)));

            // Loại bỏ header và footer
            key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                     .replace("-----END PUBLIC KEY-----", "")
                     .replaceAll("\\s", ""); // Loại bỏ các khoảng trắng và dòng trống

            // Giải mã chuỗi base64
            byte[] keyBytes = Base64.getDecoder().decode(key);

            // Tạo X509EncodedKeySpec và khóa
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để debug
            throw e; // Ném lại ngoại lệ để xử lý ở nơi gọi
        }
    }
}

