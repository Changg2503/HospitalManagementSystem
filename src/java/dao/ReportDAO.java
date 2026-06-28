/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 *
 * @author Nguyen Trang
 */
public class ReportDAO extends DBContext {

    //-------------------------1.Lịch khám theo trạng thái-------------------------
    public List<Object[]> appointmentByStatus() {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT Status,
                   COUNT(*) AS Total
            FROM Appointment
            GROUP BY Status
            """;

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("Status"),
                    rs.getInt("Total")
                };
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //-------------------------2.Top bác sĩ-------------------------
    public List<Object[]> topDoctor() {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT TOP 5
                   d.FullName,
                   COUNT(*) AS Total
            FROM Appointment a
            JOIN Doctor d
            ON a.DoctorID = d.DoctorID
            GROUP BY d.FullName
            ORDER BY Total DESC
            """;

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("FullName"),
                    rs.getInt("Total")
                };

                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //-------------------------3.Số ca khám theo khoa-------------------------
    public List<Object[]> appointmentByDepartment() {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT dp.DepartmentName,
                   COUNT(*) AS Total
            FROM Appointment a
            JOIN Doctor d
            ON a.DoctorID = d.DoctorID
            JOIN Department dp
            ON d.DepartmentID = dp.DepartmentID
            GROUP BY dp.DepartmentName
            """;

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("DepartmentName"),
                    rs.getInt("Total")
                };

                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //-------------------------4.Doanh thu theo tháng-------------------------
    public List<Object[]> revenueByMonth() {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT MONTH(PaymentDate) AS Month,
                   SUM(Amount) AS Revenue
            FROM Invoice
            GROUP BY MONTH(PaymentDate)
            """;

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    "Tháng " + rs.getInt("Month"),
                    rs.getInt("Revenue")
                };

                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //-------------------------5.Số bệnh án được tạo theo tháng-------------------------
    public List<Object[]> medicalRecordByMonth() {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT MONTH(CreatedDate) AS Month,
                   COUNT(*) AS Total
            FROM MedicalRecord
            GROUP BY MONTH(CreatedDate)
            ORDER BY Month
            """;

        try {
            PreparedStatement ps
                    = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    "Tháng " + rs.getInt("Month"),
                    rs.getInt("Total")
                };

                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
