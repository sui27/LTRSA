/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_cuoicung;


import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // Tạo và khởi động registry trên cổng 3000
            LocateRegistry.createRegistry(3000);

            // Tạo một đối tượng thực thi của đối tượng từ xa
            IUsersService iUsers = new IUsersServiceImpl();

            // Đăng ký đối tượng từ xa vào registry
            String rmiUrl = "rmi://localhost:3000/testHUFLIT";
            Naming.rebind(rmiUrl, iUsers);

            System.out.println("Máy chủ RMI đang chạy...");
        } catch (Exception e) {
            System.err.println("Lỗi máy chủ RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


