/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.MedicalRecordDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.MedicalRecord;

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

                List<MedicalRecord> list = dao.paging(page);

                int total = dao.getTotal();

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
            case "insert":

                try {

                int patientID = Integer.parseInt(
                        request.getParameter("patientID"));

                int doctorID = Integer.parseInt(
                        request.getParameter("doctorID"));

                String diagnosis
                        = request.getParameter("diagnosis");

                String treatment
                        = request.getParameter("treatment");

                MedicalRecord m = new MedicalRecord();

                m.setPatientID(patientID);
                m.setDoctorID(doctorID);
                m.setDiagnosis(diagnosis);
                m.setTreatment(treatment);

                dao.insert(m);

            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect("medicalRecord?action=list");

            break;

            //================ UPDATE =================
            case "update":

                try {

                int recordID = Integer.parseInt(
                        request.getParameter("recordID"));

                int patientID = Integer.parseInt(
                        request.getParameter("patientID"));

                int doctorID = Integer.parseInt(
                        request.getParameter("doctorID"));

                String diagnosis
                        = request.getParameter("diagnosis");

                String treatment
                        = request.getParameter("treatment");

                MedicalRecord m = new MedicalRecord();

                m.setRecordID(recordID);
                m.setPatientID(patientID);
                m.setDoctorID(doctorID);
                m.setDiagnosis(diagnosis);
                m.setTreatment(treatment);

                dao.update(m);

            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect("medicalRecord?action=list");

            break;

            //================ DELETE =================
            case "delete":

                try {

                int id = Integer.parseInt(
                        request.getParameter("id"));

                dao.delete(id);

            } catch (Exception e) {
                e.printStackTrace();
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

}
