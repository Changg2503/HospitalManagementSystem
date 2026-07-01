package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Appointment;
import utils.DBContext;

public class AppointmentDAO extends DBContext {

    public ArrayList<Appointment> getAllAppointment() {

        ArrayList<Appointment> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM Appointment";

        try {

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Appointment a =
                        new Appointment();

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

    public void addAppointment(Appointment a) {

        String sql =
                "INSERT INTO Appointment "
                + "(PatientID, DoctorID, AppointmentDate, Status) "
                + "VALUES(?,?,?,?)";

        try {

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ps.setInt(1, a.getPatientID());

            ps.setInt(2, a.getDoctorID());

            ps.setString(3, a.getAppointmentDate());

            ps.setString(4, a.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateStatus(int id,
            String status) {

        String sql =
                "UPDATE Appointment "
                + "SET Status=? "
                + "WHERE AppointmentID=?";

        try {

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ps.setString(1, status);

            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}