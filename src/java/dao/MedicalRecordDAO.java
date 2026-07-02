/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.MedicalRecord;
import model.Patient;
import utils.DBContext;

/**
 *
 * @author Nguyen Trang
 */
public class MedicalRecordDAO extends DBContext {

    //-------------------------lấy tất cả danh sách -------------------------
    public List<MedicalRecord> getAll() {
        List<MedicalRecord> list = new ArrayList<>();

        String sql = "SELECT * FROM MedicalRecord";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new MedicalRecord(
                        rs.getInt("RecordID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getString("Diagnosis"),
                        rs.getString("Treatment"),
                        rs.getTimestamp("CreatedDate")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //------------------------- search theo id-------------------------
    public MedicalRecord getByID(int id) {
        String sql
                = "SELECT * FROM MedicalRecord WHERE RecordID=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new MedicalRecord(
                        rs.getInt("RecordID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getString("Diagnosis"),
                        rs.getString("Treatment"),
                        rs.getTimestamp("CreatedDate")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // -------------------------thêm-------------------------
    public void insert(MedicalRecord m) {
        String sql = """
                 INSERT INTO MedicalRecord
                 (PatientID, DoctorID, Diagnosis, Treatment)
                 VALUES (?, ?, ?, ?)
                 """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, m.getPatientID());
            ps.setInt(2, m.getDoctorID());
            ps.setString(3, m.getDiagnosis());
            ps.setString(4, m.getTreatment());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------cập nhật-------------------------
    public void update(MedicalRecord m) {
        String sql = """
                 UPDATE MedicalRecord
                 SET PatientID=?,
                     DoctorID=?,
                     Diagnosis=?,
                     Treatment=?
                 WHERE RecordID=?
                 """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, m.getPatientID());
            ps.setInt(2, m.getDoctorID());
            ps.setString(3, m.getDiagnosis());
            ps.setString(4, m.getTreatment());
            ps.setInt(5, m.getRecordID());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------Xóa-------------------------
    public void delete(int id) {
        String sql
                = "DELETE FROM MedicalRecord WHERE RecordID=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------search theo tên bệnh nhân-------------------------
    public List<MedicalRecord> search(String keyword) {
        List<MedicalRecord> list = new ArrayList<>();

        String sql = """
                 SELECT m.*
                 FROM MedicalRecord m
                 JOIN Patient p
                 ON m.PatientID = p.PatientID
                 WHERE p.FullName LIKE ?
                 """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new MedicalRecord(
                        rs.getInt("RecordID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getString("Diagnosis"),
                        rs.getString("Treatment"),
                        rs.getTimestamp("CreatedDate")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getTotal() {
        String sql = "SELECT COUNT(*) FROM MedicalRecord";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<MedicalRecord> paging(int page) {

        List<MedicalRecord> list = new ArrayList<>();

        String sql = """
                 SELECT *
                 FROM MedicalRecord
                 ORDER BY RecordID
                 OFFSET ? ROWS
                 FETCH NEXT 5 ROWS ONLY
                 """;

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, (page - 1) * 5);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new MedicalRecord(
                        rs.getInt("RecordID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getString("Diagnosis"),
                        rs.getString("Treatment"),
                        rs.getTimestamp("CreatedDate")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    /*
    //-------------------------lấy danh sách bệnh nhân-------------------------
    public List<Patient> getAllPatient() {
        List<Patient> list = new ArrayList<>();

        String sql = "SELECT * FROM Patient";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("PatientID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("DOB"),
                        rs.getString("Phone"),
                        rs.getString("Address")
                );

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //-------------------------lấy danh sách bác sĩ -------------------------
    public List<Doctor> getAllDoctor() {
        List<Doctor> list = new ArrayList<>();

        String sql = "SELECT * FROM Doctor";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Doctor d = new Doctor(
                        rs.getInt("DoctorID"),
                        rs.getString("FullName"),
                        rs.getString("Specialization"),
                        rs.getString("Phone"),
                        rs.getInt("DepartmentID")
                );

                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
     */
}
