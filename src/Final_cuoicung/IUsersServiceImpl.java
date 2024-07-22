/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_cuoicung;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class IUsersServiceImpl extends UnicastRemoteObject implements IUsersService {

    protected IUsersServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<User> Getallusers() throws RemoteException {
        List<User> userList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDatabase.getConnection();

            // Chuẩn bị câu truy vấn SQL
            String sql = "SELECT * FROM users";
            stmt = conn.prepareStatement(sql);

            // Thực thi truy vấn và nhận kết quả
            rs = stmt.executeQuery();

            // Duyệt qua các dòng kết quả và thêm vào danh sách User
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullname");
                User user = new User(id, fullName);
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng các đối tượng ResultSet, PreparedStatement và Connection
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return userList;
    }

    @Override
    public void UpdateUser(User user) throws RemoteException {
        Connection conn = null;
        PreparedStatement stmt = null;

    try {
        conn = ConnectionDatabase.getConnection();

        // Chuẩn bị câu truy vấn SQL để cập nhật thông tin người dùng
        String sql = "UPDATE users SET fullname = ? WHERE id = ?";
        stmt = conn.prepareStatement(sql);
        System.out.println(">>>>>>>: " +user.getFullname());
        stmt.setString(1, user.getFullname());
        stmt.setInt(2, user.getId());

        // Thực thi truy vấn
        stmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // Đóng PreparedStatement và Connection
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

    @Override
    public void AddUser(String Username) throws RemoteException {
        try {
        Connection conn = ConnectionDatabase.getConnection();
        String sql = "INSERT INTO  `users`(`fullname`) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, Username);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void DeleteUser(int UserID) throws RemoteException {
        try {
        Connection conn = ConnectionDatabase.getConnection();
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, UserID);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
}

