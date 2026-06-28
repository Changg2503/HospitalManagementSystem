/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.Doctor;
import utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author PC
 */
public class DoctorDAO extends DBContext{
    
    
//LẤY DANH SÁCH BÁC SĨ
    public List<Doctor> getAllDoctors(){

    List<Doctor> list = new ArrayList<>();

    String sql="SELECT * FROM Doctor";

    try{

        PreparedStatement st=
        connection.prepareStatement(sql);

        ResultSet rs=st.executeQuery();

        while(rs.next()){

            list.add(new Doctor(
                    rs.getInt("DoctorID"),
                    rs.getString("FullName"),
                    rs.getString("Specialization"),
                    rs.getString("Phone"),
                    rs.getInt("DepartmentID")
            ));
        }

    }catch(Exception e){}

    return list;
}
   
    
    
//INSERT DOCTOR
    public void insertDoctor(Doctor d){

    String sql=
    "INSERT INTO Doctor"
    + "(FullName,Specialization,Phone,DepartmentID)"
    + " VALUES(?,?,?,?)";

    try{

        PreparedStatement st=
        connection.prepareStatement(sql);

        st.setString(1,d.getFullName());
        st.setString(2,d.getSpecialization());
        st.setString(3,d.getPhone());
        st.setInt(4,d.getDepartmentID());

        st.executeUpdate();

    }catch(Exception e){}
}
    
    
//UPDATE DOCTOR
    public void updateDoctor(Doctor d){

    String sql=
    "UPDATE Doctor "
    + "SET FullName=?,"
    + "Specialization=?,"
    + "Phone=?,"
    + "DepartmentID=? "
    + "WHERE DoctorID=?";

    try{

        PreparedStatement st=
        connection.prepareStatement(sql);

        st.setString(1,d.getFullName());
        st.setString(2,d.getSpecialization());
        st.setString(3,d.getPhone());
        st.setInt(4,d.getDepartmentID());
        st.setInt(5,d.getDoctorID());

        st.executeUpdate();

    }catch(Exception e){}
}
   
    
    

//DELETE DOCTOR
    public void deleteDoctor(int id){

    String sql=
    "DELETE FROM Doctor WHERE DoctorID=?";

    try{

        PreparedStatement st=
        connection.prepareStatement(sql);

        st.setInt(1,id);

        st.executeUpdate();

    }catch(Exception e){}
}
}
