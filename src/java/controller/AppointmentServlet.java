package controller;

import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import utils.Validator;

/**
 *
 * @author admin
 */
public class AppointmentServlet extends HttpServlet {

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
            out.println("<title>Servlet AppointmentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AppointmentServlet at " + request.getContextPath() + "</h1>");
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

        AppointmentDAO dao = new AppointmentDAO();

        String action = request.getParameter("action");

        // Xác định Patient hiện tại (nếu role = Patient) để giới hạn dữ liệu
        model.Account account = (model.Account) request.getSession().getAttribute("ACCOUNT");
        Integer myPatientID = null;

        if (account != null && "Patient".equals(account.getRole())) {
            model.Patient myPatient = new dao.PatientDAO().getByAccountID(account.getAccountID());
            if (myPatient != null) {
                myPatientID = myPatient.getPatientID();
            }
        }

        //================ LIST + PAGINATION =================
        if (action == null) {
            action = "list";
        }
        if ("list".equals(action)) {

            int index = 1;

            String page
                    = request.getParameter("page");

            if (page != null) {
                index = Integer.parseInt(page);
            }

            List<Appointment> list;
            int count;

            if (myPatientID != null) {
                // Role Patient: chỉ xem lịch hẹn của chính mình
                list = dao.pagingByPatient(myPatientID, index);
                count = dao.getTotalAppointmentByPatient(myPatientID);
            } else {
                // Admin / Doctor: xem tất cả
                list = dao.paging(index);
                count = dao.getTotalAppointment();
            }

            int endPage = count / 5;

            if (count % 5 != 0) {
                endPage++;
            }

            request.setAttribute("list", list);
            request.setAttribute("currentPage", index);
            request.setAttribute("endPage", endPage);

            request.getRequestDispatcher("jsp/appointment/list.jsp").forward(request, response);
                    
                    
        } //================ OPEN CREATE FORM =================
        else if ("add".equals(action)) {

            request.getRequestDispatcher(
                    "jsp/appointment/form.jsp").forward(request, response);

        } else if ("edit".equals(action)) {

            int id = Integer.parseInt(
                    request.getParameter("id"));

            Appointment a = dao.getAppointmentById(id);

            request.setAttribute("appointment", a);

            request.getRequestDispatcher(
                    "jsp/appointment/form.jsp")
                    .forward(request, response);
        }//================ CANCEL APPOINTMENT =================
        else if ("cancel".equals(action)) {

            int id
                    = Integer.parseInt(
                            request.getParameter("id"));

            // Role Patient: chỉ được hủy lịch hẹn của chính mình
            if (myPatientID != null) {
                Appointment target = dao.getAppointmentById(id);
                if (target == null || target.getPatientID() != myPatientID) {
                    request.getSession().setAttribute("FLASH_ERROR",
                            "You are not allowed to cancel this appointment.");
                    response.sendRedirect("appointment");
                    return;
                }
            }

            dao.updateStatus(id, "Cancelled");

            request.getSession().setAttribute("FLASH_SUCCESS", "Appointment cancelled.");

            response.sendRedirect("appointment");
        } //================ COMPLETE APPOINTMENT =================
        else if ("complete".equals(action)) {

            int id
                    = Integer.parseInt(
                            request.getParameter("id"));

            dao.updateStatus(id, "Completed");

            request.getSession().setAttribute("FLASH_SUCCESS", "Appointment marked as completed.");

            response.sendRedirect("appointment");
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

        AppointmentDAO dao = new AppointmentDAO();

        String action = request.getParameter("action");

        if (action == null) {
            action = "insert";
        }

        switch (action) {

            //================ DELETE =================
            case "delete": {

                try {
                    int id = Integer.parseInt(
                            request.getParameter("id"));

                    dao.deleteAppointment(id);

                    request.getSession().setAttribute("FLASH_SUCCESS", "Appointment deleted successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("FLASH_ERROR", "Failed to delete appointment.");
                }

                response.sendRedirect("appointment");
                break;
            }

            //================ UPDATE =================
            case "update": {

                int appointmentID = Integer.parseInt(
                        request.getParameter("appointmentID"));

                String patientIDStr = request.getParameter("patientID");
                String doctorIDStr = request.getParameter("doctorID");
                String appointmentDate = request.getParameter("appointmentDate");
                String status = request.getParameter("status");

                List<String> errors = validateAppointment(
                        patientIDStr, doctorIDStr, appointmentDate);

                if (!errors.isEmpty()) {

                    Appointment a = new Appointment();
                    a.setAppointmentID(appointmentID);
                    a.setAppointmentDate(appointmentDate);
                    a.setStatus(status);
                    try {
                        a.setPatientID(Integer.parseInt(patientIDStr));
                        a.setDoctorID(Integer.parseInt(doctorIDStr));
                    } catch (Exception ignore) {
                    }

                    request.setAttribute("errors", errors);
                    request.setAttribute("appointment", a);

                    request.getRequestDispatcher("jsp/appointment/form.jsp")
                            .forward(request, response);
                    return;
                }

                try {
                    int patientID = Integer.parseInt(patientIDStr);
                    int doctorID = Integer.parseInt(doctorIDStr);

                    Appointment a = new Appointment();

                    a.setAppointmentID(appointmentID);
                    a.setPatientID(patientID);
                    a.setDoctorID(doctorID);
                    a.setAppointmentDate(appointmentDate);
                    a.setStatus(status);

                    dao.updateAppointment(a);

                    request.getSession().setAttribute("FLASH_SUCCESS", "Appointment updated successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("FLASH_ERROR", "Failed to update appointment.");
                }

                response.sendRedirect("appointment");
                break;
            }

            //================ INSERT (default) =================
            default: {

                String patientIDStr = request.getParameter("patientID");
                String doctorIDStr = request.getParameter("doctorID");
                String appointmentDate = request.getParameter("appointmentDate");

                List<String> errors = validateAppointment(
                        patientIDStr, doctorIDStr, appointmentDate);

                if (!errors.isEmpty()) {

                    Appointment a = new Appointment();
                    a.setAppointmentDate(appointmentDate);
                    try {
                        a.setPatientID(Integer.parseInt(patientIDStr));
                        a.setDoctorID(Integer.parseInt(doctorIDStr));
                    } catch (Exception ignore) {
                    }

                    request.setAttribute("errors", errors);
                    request.setAttribute("appointment", a);

                    request.getRequestDispatcher("jsp/appointment/form.jsp")
                            .forward(request, response);
                    return;
                }

                try {
                    int patientID = Integer.parseInt(patientIDStr);
                    int doctorID = Integer.parseInt(doctorIDStr);

                    Appointment a = new Appointment();

                    a.setPatientID(patientID);
                    a.setDoctorID(doctorID);
                    a.setAppointmentDate(appointmentDate);
                    a.setStatus("Pending");

                    dao.addAppointment(a);

                    request.getSession().setAttribute("FLASH_SUCCESS", "Appointment added successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("FLASH_ERROR", "Failed to add appointment.");
                }

                response.sendRedirect("appointment");
                break;
            }
        }
    }

    // ===============================
    // VALIDATE APPOINTMENT FORM DATA (server-side)
    // ===============================
    private List<String> validateAppointment(
            String patientIDStr, String doctorIDStr, String appointmentDate) {

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

        if (!Validator.isRequired(appointmentDate)) {
            errors.add("Appointment date/time cannot be empty.");
        } else if (!Validator.isValidDateTime(appointmentDate)) {
            errors.add("Appointment date/time format is invalid.");
        }

        return errors;
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

}