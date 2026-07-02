/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Patient;
import utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Nguyen Trang
 */
public class PatientDAO extends DBContext {

    public List<Patient> getAll() {

        List<Patient> list = new ArrayList<>();

        String sql = "SELECT * FROM Patient";

        try {

            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Patient p = new Patient(
                        rs.getInt("PatientID"),
                        rs.getInt("AccountID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("DOB"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address"));

                list.add(p);
            }

        } catch (Exception e) {
        }

        return list;
    }
//Lấy theo ID

    public Patient getById(int id) {

        String sql
                = "SELECT * FROM Patient WHERE PatientID=?";

        try {

            PreparedStatement st
                    = connection.prepareStatement(sql);

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {

                return new Patient(
                        rs.getInt("PatientID"),
                        rs.getInt("AccountID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("DOB"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address"));
            }

        } catch (Exception e) {
        }

        return null;
    }
//Insert

    public void insert(Patient p) {

        String sql
                = "INSERT INTO Patient\n"
                + "(AccountID,FullName,Gender,DOB,Phone,Email,Address)\n"
                + "VALUES (?,?,?,?,?,?,?)";

        try {

            PreparedStatement st
                    = connection.prepareStatement(sql);

            st.setInt(1, p.getAccountID());
            st.setString(2, p.getFullName());
            st.setString(3, p.getGender());
            st.setDate(4, new java.sql.Date(p.getDob().getTime()));
            st.setString(5, p.getPhone());
            st.setString(6, p.getEmail());
            st.setString(7, p.getAddress());

            st.executeUpdate();

        } catch (Exception e) {
        }
    }
//Update

    public void update(Patient p) {

        String sql
                = "UPDATE Patient\n"
                + "SET AccountID=?,\n"
                + "    FullName=?,\n"
                + "    Gender=?,\n"
                + "    DOB=?,\n"
                + "    Phone=?,\n"
                + "    Email=?,\n"
                + "    Address=?\n"
                + "WHERE PatientID=?";

        try {

            PreparedStatement st
                    = connection.prepareStatement(sql);

            st.setInt(1, p.getAccountID());
            st.setString(2, p.getFullName());
            st.setString(3, p.getGender());
            st.setDate(4, new java.sql.Date(p.getDob().getTime()));
            st.setString(5, p.getPhone());
            st.setString(6, p.getEmail());
            st.setString(7, p.getAddress());
            st.setInt(8, p.getPatientID());
            st.executeUpdate();

        } catch (Exception e) {
        }
    }
//Delete

    public void delete(int id) {

        String sql
                = "DELETE FROM Patient WHERE PatientID=?";

        try {

            PreparedStatement st
                    = connection.prepareStatement(sql);

            st.setInt(1, id);

            st.executeUpdate();

        } catch (Exception e) {
        }
    }

//Phân trang
    public List<Patient> paging(int index) {

        List<Patient> list = new ArrayList<>();

        String sql = "SELECT * FROM Patient "
                + "ORDER BY PatientID "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, (index - 1) * 5);
            st.setInt(2, 5);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("PatientID"),
                        rs.getInt("AccountID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("DOB"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address")
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

//SEARCH PATIENT 
    public List<Patient> search(String keyword) {

        List<Patient> list = new ArrayList<>();

        String sql
                = "SELECT * FROM Patient "
                + "WHERE FullName LIKE ? "
                + "OR CAST(PatientID AS VARCHAR) LIKE ? ";

        try {

            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, "%" + keyword + "%");
            st.setString(2, "%" + keyword + "%");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                list.add(new Patient(
                        rs.getInt("PatientID"),
                        rs.getInt("AccountID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("DOB"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

//ĐẾM SỐ BÊNH NHÂN(ĐỂ BIẾT CÓ BAO NHIÊU TRANG)
    public int getTotal() {

        String sql = "SELECT COUNT(*) FROM Patient";

        try {

            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}
