package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Department;
import utils.DBContext;

public class DepartmentDAO extends DBContext {

    // Get all departments 
    public ArrayList<Department> getAllDepartment() {

        ArrayList<Department> list = new ArrayList<>();

        String sql = "SELECT * FROM Department";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Department d = new Department(
                        rs.getInt("DepartmentID"),
                        rs.getString("DepartmentName")
                );

                list.add(d);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    // Get department by id  
    public Department getDepartmentById(int id) {

        String sql = "SELECT * FROM Department WHERE DepartmentID=?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Department(
                        rs.getInt("DepartmentID"),
                        rs.getString("DepartmentName")
                );
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    // Add department 
    public void addDepartment(Department d) {

        String sql = "INSERT INTO Department(DepartmentName) VALUES(?)";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, d.getDepartmentName());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

     // UpdateDepartment 
    public void updateDepartment(Department d) {

        String sql = "UPDATE Department "
                + "SET DepartmentName=? "
                + "WHERE DepartmentID=?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, d.getDepartmentName());
            ps.setInt(2, d.getDepartmentID());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Delete department  
    public void deleteDepartment(int id) {

        String sql = "DELETE FROM Department WHERE DepartmentID=?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // COUNT TOTAL DEPARTMENTS 
    public int getTotalDepartment() {

        String sql = "SELECT COUNT(*) FROM Department";

        try {

            PreparedStatement st
                    = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // PAGINATION 
    public List<Department> paging(int index) {

        List<Department> list = new ArrayList<>();

        String sql = "SELECT * FROM Department "
                + "ORDER BY DepartmentID "
                + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try {

            PreparedStatement st
                    = connection.prepareStatement(sql);

            st.setInt(1, (index - 1) * 5);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Department d = new Department(
                        rs.getInt("DepartmentID"),
                        rs.getString("DepartmentName")
                );

                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    } 
}
