package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import utils.DBContext;

public class AppointmentDAO extends DBContext {

    // Convert the raw "yyyy-MM-ddTHH:mm" string coming from
    // <input type="datetime-local"> into a proper java.sql.Timestamp
    // so it is bound correctly to the DATETIME column (no implicit
    // string-to-date conversion issues on the DB side).
    private Timestamp parseTimestamp(String dateTimeStr) {

        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }

        String s = dateTimeStr.trim();

        // Some browsers send seconds, some don't -> normalize
        if (s.length() == 16) { // yyyy-MM-ddTHH:mm
            s = s + ":00";
        }

        LocalDateTime ldt = LocalDateTime.parse(
                s, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        return Timestamp.valueOf(ldt);
    }

    // Format a Timestamp coming from DB back into "yyyy-MM-ddTHH:mm"
    // so it displays correctly in <input type="datetime-local"> and in lists.
    private String formatTimestamp(Timestamp ts) {

        if (ts == null) {
            return "";
        }

        return ts.toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    // Get 5 most recent appointments (for dashboard widget)
    public List<Appointment> getRecentAppointments() {

        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT TOP 5 * FROM Appointment "
                + "ORDER BY AppointmentID DESC";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Appointment a = new Appointment();

                a.setAppointmentID(rs.getInt("AppointmentID"));
                a.setPatientID(rs.getInt("PatientID"));
                a.setDoctorID(rs.getInt("DoctorID"));
                a.setAppointmentDate(formatTimestamp(rs.getTimestamp("AppointmentDate")));
                a.setStatus(rs.getString("Status"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

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
                        formatTimestamp(rs.getTimestamp("AppointmentDate")));

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
            ps.setTimestamp(3, parseTimestamp(a.getAppointmentDate()));
            ps.setString(4, a.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
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
                        formatTimestamp(rs.getTimestamp("AppointmentDate")),
                        rs.getString("Status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update full appointment (used by edit form)
    public void updateAppointment(Appointment a) {

        String sql
                = "UPDATE Appointment "
                + "SET PatientID=?, DoctorID=?, AppointmentDate=?, Status=? "
                + "WHERE AppointmentID=?";

        try {

            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ps.setInt(1, a.getPatientID());
            ps.setInt(2, a.getDoctorID());
            ps.setTimestamp(3, parseTimestamp(a.getAppointmentDate()));
            ps.setString(4, a.getStatus());
            ps.setInt(5, a.getAppointmentID());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete appointment
    public void deleteAppointment(int id) {

        String sql = "DELETE FROM Appointment WHERE AppointmentID=?";

        try {

            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        formatTimestamp(rs.getTimestamp("AppointmentDate")));

                a.setStatus(
                        rs.getString("Status"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===============================
    // PATIENT-SCOPED QUERIES
    // Dùng cho role Patient: chỉ xem lịch hẹn của chính mình
    // ===============================
    public List<Appointment> pagingByPatient(int patientID, int index) {

        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM Appointment "
                + "WHERE PatientID = ? "
                + "ORDER BY AppointmentID "
                + "OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try {

            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, patientID);
            st.setInt(2, (index - 1) * 5);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Appointment a = new Appointment();

                a.setAppointmentID(rs.getInt("AppointmentID"));
                a.setPatientID(rs.getInt("PatientID"));
                a.setDoctorID(rs.getInt("DoctorID"));
                a.setAppointmentDate(formatTimestamp(rs.getTimestamp("AppointmentDate")));
                a.setStatus(rs.getString("Status"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getTotalAppointmentByPatient(int patientID) {

        String sql = "SELECT COUNT(*) FROM Appointment WHERE PatientID = ?";

        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, patientID);

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
