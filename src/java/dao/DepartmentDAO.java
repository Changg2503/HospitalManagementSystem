package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Department;
import utils.DBContext;

public class DepartmentDAO extends DBContext {

    public ArrayList<Department> getAllDepartment() {

        ArrayList<Department> list = new ArrayList<>();

        String sql = "SELECT * FROM Department";

        try {

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Department d =
                        new Department(
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

    public Department getDepartmentById(int id) {

        String sql =
                "SELECT * FROM Department WHERE DepartmentID=?";

        try {

            PreparedStatement ps =
                    connection.prepareStatement(sql);

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

    public void addDepartment(Department d) {

        String sql =
                "INSERT INTO Department(DepartmentName) VALUES(?)";

        try {

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ps.setString(1, d.getDepartmentName());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateDepartment(Department d) {

        String sql =
                "UPDATE Department "
                + "SET DepartmentName=? "
                + "WHERE DepartmentID=?";

        try {

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ps.setString(1, d.getDepartmentName());

            ps.setInt(2, d.getDepartmentID());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteDepartment(int id) {

        String sql =
                "DELETE FROM Department WHERE DepartmentID=?";

        try {

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}