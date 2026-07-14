/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DoctorDAO;
import dao.MedicalRecordDAO;
import dao.PatientDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.MedicalRecord;
import utils.Validator;

/**
 *
 * @author Nguyen Trang
 */
public class MedicalRecordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MedicalRecordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MedicalRecordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MedicalRecordDAO dao = new MedicalRecordDAO();

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        // Xác định Patient hiện tại (nếu role = Patient) để giới hạn dữ liệu
        model.Account account = (model.Account) request.getSession().getAttribute("ACCOUNT");
        Integer myPatientID = null;

        if (account != null && "Patient".equals(account.getRole())) {
            model.Patient myPatient = new PatientDAO().getByAccountID(account.getAccountID());
            if (myPatient != null) {
                myPatientID = myPatient.getPatientID();
            }
        }

        switch (action) {

            //================ LIST + PAGINATION =================
            case "list":

                int page = 1;

                try {
                    String index = request.getParameter("page");
                    if (index != null) {
                        page = Integer.parseInt(index);
                    }
                } catch (Exception e) {
                    page = 1;
                }

                List<MedicalRecord> list;
                int total;

                if (myPatientID != null) {
                    // Role Patient: chỉ xem hồ sơ bệnh án của chính mình
                    list = dao.pagingByPatient(myPatientID, page);
                    total = dao.getTotalByPatient(myPatientID);
                } else {
                    // Admin / Doctor: xem tất cả
                    list = dao.paging(page);
                    total = dao.getTotal();
                }

                int endPage = total / 5;

                if (total % 5 != 0) {
                    endPage++;
                }

                request.setAttribute("listRecord", list);
                request.setAttribute("currentPage", page);
                request.setAttribute("endPage", endPage);

                request.getRequestDispatcher("jsp/medicalRecord/list.jsp")
                        .forward(request, response);

                break;

            //================ SEARCH =================
            case "search":

                String keyword = request.getParameter("keyword");

                if (keyword == null) {
                    keyword = "";
                }

                List<MedicalRecord> result = dao.search(keyword);

                // Role Patient: chỉ được thấy record của chính mình trong kết quả search
                if (myPatientID != null) {
                    final int pid = myPatientID;
                    result.removeIf(r -> r.getPatientID() != pid);
                }

                request.setAttribute("listRecord", result);
                request.setAttribute("keyword", keyword);

                request.getRequestDispatcher("jsp/medicalRecord/list.jsp")
                        .forward(request, response);

                break;

            //================ OPEN ADD FORM =================
            case "add":

                request.getRequestDispatcher("jsp/medicalRecord/form.jsp")
                        .forward(request, response);

                break;

            //================ OPEN EDIT FORM =================
            case "edit":

                try {

                int id = Integer.parseInt(request.getParameter("id"));

                MedicalRecord record = dao.getByID(id);

                // Role Patient: chặn xem/sửa record không phải của mình
                if (myPatientID != null
                        && (record == null || record.getPatientID() != myPatientID)) {
                    response.sendRedirect("medicalRecord?action=list");
                    return;
                }

                request.setAttribute("record", record);

                request.getRequestDispatcher("jsp/medicalRecord/form.jsp")
                        .forward(request, response);

            } catch (Exception e) {

                response.sendRedirect("medicalRecord?action=list");

            }

            break;

            default:

                response.sendRedirect("medicalRecord?action=list");

                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MedicalRecordDAO dao = new MedicalRecordDAO();

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {

            //================ INSERT =================
            case "insert": {

                String patientIDStr = request.getParameter("patientID");
                String doctorIDStr = request.getParameter("doctorID");
                String diagnosis = request.getParameter("diagnosis");
                String treatment = request.getParameter("treatment");

                List<String> errors = validateRecord(
                        patientIDStr, doctorIDStr, diagnosis);

                if (!errors.isEmpty()) {

                    MedicalRecord m = new MedicalRecord();
                    m.setDiagnosis(diagnosis);
                    m.setTreatment(treatment);
                    try {
                        m.setPatientID(Integer.parseInt(patientIDStr));
                        m.setDoctorID(Integer.parseInt(doctorIDStr));
                    } catch (Exception ignore) {
                    }

                    request.setAttribute("errors", errors);
                    request.setAttribute("record", m);

                    request.getRequestDispatcher("jsp/medicalRecord/form.jsp")
                            .forward(request, response);
                    return;
                }

                try {

                    int patientID = Integer.parseInt(patientIDStr);
                    int doctorID = Integer.parseInt(doctorIDStr);

                    MedicalRecord m = new MedicalRecord();

                    m.setPatientID(patientID);
                    m.setDoctorID(doctorID);
                    m.setDiagnosis(diagnosis.trim());
                    m.setTreatment(treatment);

                    dao.insert(m);

                    request.getSession().setAttribute("FLASH_SUCCESS", "Medical record added successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("FLASH_ERROR", "Failed to add medical record.");
                }

                response.sendRedirect("medicalRecord?action=list");

                break;
            }

            //================ UPDATE =================
            case "update": {

                int recordID = Integer.parseInt(
                        request.getParameter("recordID"));

                String patientIDStr = request.getParameter("patientID");
                String doctorIDStr = request.getParameter("doctorID");
                String diagnosis = request.getParameter("diagnosis");
                String treatment = request.getParameter("treatment");

                List<String> errors = validateRecord(
                        patientIDStr, doctorIDStr, diagnosis);

                if (!errors.isEmpty()) {

                    MedicalRecord m = new MedicalRecord();
                    m.setRecordID(recordID);
                    m.setDiagnosis(diagnosis);
                    m.setTreatment(treatment);
                    try {
                        m.setPatientID(Integer.parseInt(patientIDStr));
                        m.setDoctorID(Integer.parseInt(doctorIDStr));
                    } catch (Exception ignore) {
                    }

                    request.setAttribute("errors", errors);
                    request.setAttribute("record", m);

                    request.getRequestDispatcher("jsp/medicalRecord/form.jsp")
                            .forward(request, response);
                    return;
                }

                try {

                    int patientID = Integer.parseInt(patientIDStr);
                    int doctorID = Integer.parseInt(doctorIDStr);

                    MedicalRecord m = new MedicalRecord();

                    m.setRecordID(recordID);
                    m.setPatientID(patientID);
                    m.setDoctorID(doctorID);
                    m.setDiagnosis(diagnosis.trim());
                    m.setTreatment(treatment);

                    dao.update(m);

                    request.getSession().setAttribute("FLASH_SUCCESS", "Medical record updated successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("FLASH_ERROR", "Failed to update medical record.");
                }

                response.sendRedirect("medicalRecord?action=list");

                break;
            }

            //================ DELETE =================
            case "delete":

                try {

                int id = Integer.parseInt(
                        request.getParameter("id"));

                dao.delete(id);

                request.getSession().setAttribute("FLASH_SUCCESS", "Medical record deleted successfully.");

            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("FLASH_ERROR", "Failed to delete medical record.");
            }

            response.sendRedirect("medicalRecord?action=list");

            break;

            default:

                response.sendRedirect("medicalRecord?action=list");

                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    // ===============================
    // VALIDATE MEDICAL RECORD FORM DATA (server-side)
    // ===============================
    private List<String> validateRecord(
            String patientIDStr, String doctorIDStr, String diagnosis) {

        List<String> errors = new ArrayList<>();

        if (!Validator.isRequired(patientIDStr)
                || !Validator.isValidPositiveInt(patientIDStr)) {
            errors.add("Patient ID is required and must be a valid number.");
        } else if (new PatientDAO().getById(Integer.parseInt(patientIDStr)) == null) {
            errors.add("Patient ID does not exist.");
        }

        if (!Validator.isRequired(doctorIDStr)
                || !Validator.isValidPositiveInt(doctorIDStr)) {
            errors.add("Doctor ID is required and must be a valid number.");
        } else if (new DoctorDAO().getById(Integer.parseInt(doctorIDStr)) == null) {
            errors.add("Doctor ID does not exist.");
        }

        if (!Validator.isRequired(diagnosis)) {
            errors.add("Diagnosis cannot be empty.");
        }

        return errors;
    }

}
