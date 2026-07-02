package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import utils.DBContext;

public class AppointmentDAO extends DBContext {

    // Get all appointments
    public ArrayList<Appointment> getAllAppointment() {

        ArrayList<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM Appointment";

        try {

            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Appointment a = new Appointment();

                a.setAppointmentID(
                        rs.getInt("AppointmentID"));

                a.setPatientID(
                        rs.getInt("PatientID"));

                a.setDoctorID(
                        rs.getInt("DoctorID"));

                a.setAppointmentDate(
                        rs.getString("AppointmentDate"));

                a.setStatus(
                        rs.getString("Status"));

                list.add(a);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    // Add appointment
    public void addAppointment(Appointment a) {

        String sql
                = "INSERT INTO Appointment "
                + "(PatientID, DoctorID, AppointmentDate, Status) "
                + "VALUES (?, ?, ?, ?)";

        try {

            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ps.setInt(1, a.getPatientID());
            ps.setInt(2, a.getDoctorID());
            ps.setString(3, a.getAppointmentDate());
            ps.setString(4, a.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Appointment getAppointmentById(int id) {
        String sql = """
                 SELECT *
                 FROM Appointment
                 WHERE AppointmentID = ?
                 """;

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

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
            e.printStackTrace();
        }

        return null;
    }

    // Update status
    public void updateStatus(int id, String status) {

        String sql
                = "UPDATE Appointment "
                + "SET Status=? "
                + "WHERE AppointmentID=?";

        try {

            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // COUNT TOTAL APPOINTMENTS 
    public int getTotalAppointment() {

        String sql = "SELECT COUNT(*) FROM Appointment";

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
    public List<Appointment> paging(int index) {

        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM Appointment "
                + "ORDER BY AppointmentID "
                + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try {

            PreparedStatement st
                    = connection.prepareStatement(sql);

            st.setInt(1, (index - 1) * 5);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Appointment a = new Appointment();

                a.setAppointmentID(
                        rs.getInt("AppointmentID"));

                a.setPatientID(
                        rs.getInt("PatientID"));

                a.setDoctorID(
                        rs.getInt("DoctorID"));

                a.setAppointmentDate(
                        rs.getString("AppointmentDate"));

                a.setStatus(
                        rs.getString("Status"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
