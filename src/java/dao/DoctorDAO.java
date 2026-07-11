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
    public List<Doctor> getAll(){

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

    }catch(Exception e){ e.printStackTrace();}

    return list;
}
   
    
    //Lấy theo ID
    
    public Doctor getById(int id){

    String sql = "SELECT * FROM Doctor WHERE DoctorID=?";

    try{

        PreparedStatement st = connection.prepareStatement(sql);

        st.setInt(1,id);

        ResultSet rs = st.executeQuery();

        if(rs.next()){

            return new Doctor(
                    rs.getInt("DoctorID"),
                    rs.getString("FullName"),
                    rs.getString("Specialization"),
                    rs.getString("Phone"),
                    rs.getInt("DepartmentID")
            );
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return null;
}

    
    
//INSERT DOCTOR
    public void insert(Doctor d){

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

    }catch(Exception e){ e.printStackTrace();}
}
    
    
//UPDATE DOCTOR
    public void update(Doctor d){

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

    }catch(Exception e){ e.printStackTrace();}
}
   
    
    

//DELETE DOCTOR
    public void delete(int id){

    String sql=
    "DELETE FROM Doctor WHERE DoctorID=?";

    try{

        PreparedStatement st=
        connection.prepareStatement(sql);

        st.setInt(1,id);

        st.executeUpdate();

    }catch(Exception e){ e.printStackTrace();}
}
    
    //Phân trang 
    public List<Doctor> paging(int index){

    List<Doctor> list = new ArrayList<>();

    String sql =
        "SELECT * FROM Doctor "
      + "ORDER BY DoctorID "
      + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try{

        PreparedStatement st = connection.prepareStatement(sql);

        st.setInt(1,(index-1)*5);
        st.setInt(2,5);

        ResultSet rs = st.executeQuery();

        while(rs.next()){

            list.add(new Doctor(
                    rs.getInt("DoctorID"),
                    rs.getString("FullName"),
                    rs.getString("Specialization"),
                    rs.getString("Phone"),
                    rs.getInt("DepartmentID")
            ));
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return list;
}
    
    
    
    
//SEARCH DOCTOR
    public List<Doctor> search(String keyword){

    List<Doctor> list = new ArrayList<>();

        String sql
                = "SELECT * FROM Doctor "
                + "WHERE FullName LIKE ? "
                + "OR CAST(DoctorID AS VARCHAR) LIKE ? ";

    try{

        PreparedStatement st = connection.prepareStatement(sql);

        st.setString(1,"%"+keyword+"%");
        st.setString(2,"%"+keyword+"%");

        ResultSet rs = st.executeQuery();

        while(rs.next()){

            list.add(new Doctor(
                    rs.getInt("DoctorID"),
                    rs.getString("FullName"),
                    rs.getString("Specialization"),
                    rs.getString("Phone"),
                    rs.getInt("DepartmentID")
            ));
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return list;
}
    
   
    
//ĐẾM SỐ DOCTOR
    public int getTotal(){

    String sql = "SELECT COUNT(*) FROM Doctor";

    try{

        PreparedStatement st = connection.prepareStatement(sql);

        ResultSet rs = st.executeQuery();

        if(rs.next()){
            return rs.getInt(1);
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return 0;
}
}
