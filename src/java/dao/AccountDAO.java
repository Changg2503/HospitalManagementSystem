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
public class AccountDAO extends DBContext{
    public Account login(String user,
                     String pass) {

    String sql = """
            SELECT *
            FROM Account
            WHERE Username = ?
            AND Password = ?
            AND Status = 1
            """;

    try {
        PreparedStatement ps =
                connection.prepareStatement(sql);

        ps.setString(1, user);
        ps.setString(2, pass);

        ResultSet rs =
                ps.executeQuery();

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
}
