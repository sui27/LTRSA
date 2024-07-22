/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_cuoicung;

/**
 *
 * @author Admin C:/Users/Admin/Documents/
 */
import java.io.*;
import java.net.Socket;
import java.security.PrivateKey;
import java.util.Base64;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            String message = "This is a test message";
            PrivateKey privateKey = KeyUtils.getPrivateKey("private.pem");

            // Ký số và mã hóa Base64
            String signedMessage = RSAUtils.sign(message, privateKey);
            String base64SignedMessage = Base64.getEncoder().encodeToString(signedMessage.getBytes());

            // Gửi dữ liệu đến server
            Socket socket = new Socket("localhost", 1234);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(base64SignedMessage);
            out.println(message);

            // Nhận phản hồi từ server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();
            System.out.println("Response from server: " + response);

            // Kết nối đến server RMI nếu xác thực thành công
            if (response.startsWith("VERIFIED")) {
                String[] arrResponse = response.split(";");
                String url = "rmi://localhost:3000/test" + arrResponse[1];
                System.out.println("Connecting to RMI at URL: " + url);
                // Gọi phương thức RMI từ đây
               IUsersService iUsers = (IUsersService) Naming.lookup(url);
                Menu(iUsers);
//                List<User> UserList = iUsers.Getallusers();
//                System.out.println("danh sach users:");
//                for(User user:UserList){
//                    System.out.println("ID: " + user.getId() + ", Full name: " + user.getFullname());
//                }
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Menu(IUsersService iUsers) throws RemoteException{
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. View all users");
            System.out.println("2. Add a new user");
            System.out.println("3. Update a user");
            System.out.println("4. Delete a user");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character after nextInt()

            switch (choice) {
                case 1:
                List<User> UserList = iUsers.Getallusers();
                System.out.println("danh sach users:");
                for(User user:UserList){
                    System.out.println("ID: " + user.getId() + ", Full name: " + user.getFullname());
                }
                    break;
                case 2:
                     System.out.print("Enter username to add: ");
                     String usernameToAdd = scanner.nextLine();
                     iUsers.AddUser(usernameToAdd);
                     System.out.println("User added successfully.");
                     break;
                case 3:
                    System.out.print("Enter user ID to update: ");
                    int userIdToUpdate = scanner.nextInt();
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    User updateUser = new User(userIdToUpdate, newUsername);
                    iUsers.UpdateUser(updateUser);
                    System.out.println("User updated successfully.");
                    break;
                case 4:
                    System.out.print("Enter user ID to delete: ");
                    int userIdToDelete = scanner.nextInt();
                    iUsers.DeleteUser(userIdToDelete);
                    System.out.println("User deleted successfully.");
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
        scanner.close();
    }
}

