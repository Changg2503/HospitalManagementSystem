/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.PatientDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import model.Patient;

/**
 *
 * @author Nguyen Trang
 */
public class PatientServlet extends HttpServlet {
   
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
            out.println("<title>Servlet PatientServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PatientServlet at " + request.getContextPath () + "</h1>");
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
         PatientDAO dao = new PatientDAO();
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

                List<Patient> list = dao.paging(page);

                int total = dao.getTotal();

                int endPage = total / 5;

                if (total % 5 != 0) {
                    endPage++;
                }

                request.setAttribute("listPatient", list);
                request.setAttribute("currentPage", page);
                request.setAttribute("endPage", endPage);

                request.getRequestDispatcher("/patient.jsp")
                        .forward(request, response);

                break;

            //================ SEARCH =================
            case "search":

                String keyword = request.getParameter("keyword");

                if (keyword == null) {
                    keyword = "";
                }

                List<Patient> result = dao.search(keyword);

                request.setAttribute("listPatient", result);
                request.setAttribute("keyword", keyword);

                request.getRequestDispatcher("/patient.jsp")
                        .forward(request, response);

                break;

            //================ OPEN ADD FORM =================
            case "add":

                request.getRequestDispatcher("/patient-form.jsp")
                        .forward(request, response);

                break;

            //================ OPEN EDIT FORM =================
            case "edit":

                try {

                    int id = Integer.parseInt(request.getParameter("id"));

                    Patient patient = dao.getById(id);

                    request.setAttribute("patient", patient);

                    request.getRequestDispatcher("/patient-form.jsp")
                            .forward(request, response);

                } catch (Exception e) {

                    response.sendRedirect("patient?action=list");

                }

                break;

            default:

                response.sendRedirect("patient?action=list");

                break;
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
        request.setCharacterEncoding("UTF-8");

        PatientDAO dao = new PatientDAO();
 
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {

            //================ INSERT =================
            case "insert":

                try {

                    String fullName = request.getParameter("fullName");
                    String gender = request.getParameter("gender");
                    Date dob = Date.valueOf(request.getParameter("dob"));
                    String phone = request.getParameter("phone");
                    String address = request.getParameter("address");

                    Patient p = new Patient();

                    p.setFullName(fullName);
                    p.setGender(gender);
                    p.setDob(dob);
                    p.setPhone(phone);
                    p.setAddress(address);

                    dao.insert(p);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.sendRedirect("patient?action=list");

                break;

            //================ UPDATE =================
            case "update":

                try {

                    int id = Integer.parseInt(request.getParameter("patientID"));

                    String fullName = request.getParameter("fullName");
                    String gender = request.getParameter("gender");
                    Date dob = Date.valueOf(request.getParameter("dob"));
                    String phone = request.getParameter("phone");
                    String address = request.getParameter("address");

                    Patient p = new Patient();

                    p.setPatientID(id);
                    p.setFullName(fullName);
                    p.setGender(gender);
                    p.setDob(dob);
                    p.setPhone(phone);
                    p.setAddress(address);

                    dao.update(p);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.sendRedirect("patient?action=list");

                break;

            //================ DELETE =================
            case "delete":

                try {

                    int id = Integer.parseInt(request.getParameter("id"));

                    dao.delete(id);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.sendRedirect("patient?action=list");

                break;

            default:

                response.sendRedirect("patient?action=list");

                break;

        }

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
