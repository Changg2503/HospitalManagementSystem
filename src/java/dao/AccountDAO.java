/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DBContext;

/**
 *
 * @author Nguyen Trang
 */
public class AccountDAO extends DBContext {

    public Account login(String user, String pass) {

        String sql = """
            SELECT *
            FROM Account
            WHERE Username = ?
            AND Password = ?
            AND Status = 1
            """;

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs
                    = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("AccountID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Role"),
                        rs.getBoolean("Status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createAccount(Account a) {
        String sql = "INSERT INTO Account "
                + "(Username, Password, Role, Status) "
                + "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.setString(3, a.getRole());
            ps.setBoolean(4, true);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ===============================
    // KIỂM TRA USERNAME ĐÃ TỒN TẠI
    // ===============================

    public boolean checkUsernameExist(
            String username) {

        String sql = """
            SELECT AccountID
            FROM Account
            WHERE Username = ?
            """;

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs
                    = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===============================
    // LẤY ACCOUNTID THEO USERNAME
    // ===============================
    public int getAccountIDByUsername(
            String username) {

        String sql = """
            SELECT AccountID
            FROM Account
            WHERE Username = ?
            """;

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs
                    = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("AccountID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
