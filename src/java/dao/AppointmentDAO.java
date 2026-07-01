package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Appointment;
import utils.DBContext;

public class AppointmentDAO extends DBContext {

    // Lấy tất cả lịch khám
    public ArrayList<Appointment> getAllAppointment() {

        ArrayList<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM Appointment";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Appointment a
                        = new Appointment(
                                rs.getInt("AppointmentID"),
                                rs.getInt("PatientID"),
                                rs.getInt("DoctorID"),
                                rs.getString("AppointmentDate"),
                                rs.getString("Status")
                        );

                list.add(a);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    // Tìm theo ID
    public Appointment getAppointmentById(int id) {

        String sql
                = "SELECT * FROM Appointment WHERE AppointmentID=?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Appointment(
                        rs.getInt("AppointmentID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getString("AppointmentDate"),
                        rs.getString("Status")
                );
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    // Thêm
    public void addAppointment(Appointment a) {

        String sql
                = "INSERT INTO Appointment "
                + "(PatientID,DoctorID,AppointmentDate,Status) "
                + "VALUES(?,?,?,?)";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, a.getPatientID());
            ps.setInt(2, a.getDoctorID());
            ps.setString(3, a.getAppointmentDate());
            ps.setString(4, a.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Sửa
    public void updateAppointment(Appointment a) {

        String sql
                = "UPDATE Appointment "
                + "SET PatientID=?,"
                + "DoctorID=?,"
                + "AppointmentDate=?,"
                + "Status=? "
                + "WHERE AppointmentID=?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, a.getPatientID());
            ps.setInt(2, a.getDoctorID());
            ps.setString(3, a.getAppointmentDate());
            ps.setString(4, a.getStatus());
            ps.setInt(5, a.getAppointmentID());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Xóa
    public void deleteAppointment(int id) {

        String sql
                = "DELETE FROM Appointment "
                + "WHERE AppointmentID=?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
