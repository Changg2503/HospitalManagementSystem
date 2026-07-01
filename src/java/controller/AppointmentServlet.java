package controller;

import dao.AppointmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Appointment;

/**
 *
 * @author admin
 */
@WebServlet(name="AppointmentServlet", urlPatterns={"/appointment"})
public class AppointmentServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<h1>Servlet AppointmentServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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

        // Display appointment list
        if (action == null) {

            request.setAttribute(
                    "list",
                    dao.getAllAppointment());

            request.getRequestDispatcher(
                    "appointment-list.jsp")
                    .forward(request, response);
        }

        // Open create form
        else if ("create".equals(action)) {

            request.getRequestDispatcher(
                    "appointment-form.jsp")
                    .forward(request, response);
        }

        // Cancel appointment
        else if ("cancel".equals(action)) {

            int id = Integer.parseInt(
                    request.getParameter("id"));

            dao.updateStatus(id, "Cancelled");

            response.sendRedirect("appointment");
        }

        // Mark appointment as completed
        else if ("complete".equals(action)) {

            int id = Integer.parseInt(
                    request.getParameter("id"));

            dao.updateStatus(id, "Completed");

            response.sendRedirect("appointment");
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        AppointmentDAO dao = new AppointmentDAO();

        int patientID =
                Integer.parseInt(
                        request.getParameter("patientID"));

        int doctorID =
                Integer.parseInt(
                        request.getParameter("doctorID"));

        String appointmentDate =
                request.getParameter("appointmentDate");

        Appointment a = new Appointment();

        a.setPatientID(patientID);
        a.setDoctorID(doctorID);
        a.setAppointmentDate(appointmentDate);

        // Default status when creating appointment
        a.setStatus("Pending");

        dao.addAppointment(a);

        response.sendRedirect("appointment");
    
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
