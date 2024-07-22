/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Final_cuoicung;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IUsersService extends Remote{
    public List<User> Getallusers() throws RemoteException;
    public void AddUser(String Username) throws RemoteException;
    public void DeleteUser(int UserID) throws RemoteException;
    public void UpdateUser(User user) throws RemoteException;
}
