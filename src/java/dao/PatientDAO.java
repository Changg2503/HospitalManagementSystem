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
    
    
    public List<Patient> getAllPatients() {

    List<Patient> list = new ArrayList<>();

    String sql = "SELECT * FROM Patient";

    try {

        PreparedStatement st = connection.prepareStatement(sql);

        ResultSet rs = st.executeQuery();

        while(rs.next()){

            Patient p = new Patient(
                    rs.getInt("PatientID"),
                    rs.getString("FullName"),
                    rs.getString("Gender"),
                    rs.getDate("DOB"),
                    rs.getString("Phone"),
                    rs.getString("Address"));

            list.add(p);
        }

    } catch (Exception e) {
    }

    return list;
}
//Lấy theo ID
public Patient getPatientById(int id){

    String sql =
    "SELECT * FROM Patient WHERE PatientID=?";

    try{

        PreparedStatement st =
        connection.prepareStatement(sql);

        st.setInt(1,id);

        ResultSet rs=st.executeQuery();

        if(rs.next()){

            return new Patient(
                    rs.getInt("PatientID"),
                    rs.getString("FullName"),
                    rs.getString("Gender"),
                    rs.getDate("DOB"),
                    rs.getString("Phone"),
                    rs.getString("Address"));
        }

    }catch(Exception e){}

    return null;
}
//Insert
public void insertPatient(Patient p){

    String sql=
    "INSERT INTO Patient(FullName,Gender,DOB,Phone,Address)"
            + " VALUES(?,?,?,?,?)";

    try{

        PreparedStatement st=
        connection.prepareStatement(sql);

        st.setString(1,p.getFullName());
        st.setString(2,p.getGender());
        st.setDate(3, (Date) p.getDob());
        st.setString(4,p.getPhone());
        st.setString(5,p.getAddress());

        st.executeUpdate();

    }catch(Exception e){}
}
//Update
public void updatePatient(Patient p){

    String sql=
    "UPDATE Patient "
    + "SET FullName=?,Gender=?,DOB=?,Phone=?,Address=? "
    + "WHERE PatientID=?";

    try{

        PreparedStatement st=
        connection.prepareStatement(sql);

        st.setString(1,p.getFullName());
        st.setString(2,p.getGender());
        st.setDate(3, (Date) p.getDob());
        st.setString(4,p.getPhone());
        st.setString(5,p.getAddress());

        st.setInt(6,p.getPatientID());

        st.executeUpdate();

    }catch(Exception e){}
}
//Delete
public void deletePatient(int id){

    String sql=
    "DELETE FROM Patient WHERE PatientID=?";

    try{

        PreparedStatement st=
        connection.prepareStatement(sql);

        st.setInt(1,id);

        st.executeUpdate();

    }catch(Exception e){}
}
}
