/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.DoctorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Doctor;

/**
 *
 * @author Nguyen Trang
 */
public class DoctorServlet extends HttpServlet {
   
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
            out.println("<title>Servlet DoctorServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoctorServlet at " + request.getContextPath () + "</h1>");
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
        DoctorDAO dao = new DoctorDAO();
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

                List<Doctor> list = dao.paging(page);

                int total = dao.getTotal();

                int endPage = total / 5;

                if (total % 5 != 0) {
                    endPage++;
                }

                request.setAttribute("listDoctor", list);
                request.setAttribute("currentPage", page);
                request.setAttribute("endPage", endPage);

                request.getRequestDispatcher("jsp/doctor/list.jsp")
                        .forward(request, response);

                break;

            //================ SEARCH =================
            case "search":

                String keyword = request.getParameter("keyword");

                if (keyword == null) {
                    keyword = "";
                }

                List<Doctor> result = dao.search(keyword);

                request.setAttribute("listDoctor", result);
                request.setAttribute("keyword", keyword);

                request.getRequestDispatcher("jsp/doctor/list.jsp")
                        .forward(request, response);

                break;

            //================ OPEN ADD FORM =================
            case "add":

                request.getRequestDispatcher("jsp/doctor/form.jsp")
                        .forward(request, response);

                break;

            //================ OPEN EDIT FORM =================
            case "edit":

                try {

                    int id = Integer.parseInt(request.getParameter("id"));

                    Doctor doctor = dao.getById(id);

                    request.setAttribute("doctor", doctor);

                    request.getRequestDispatcher("jsp/doctor/form.jsp")
                            .forward(request, response);

                } catch (Exception e) {

                    response.sendRedirect("doctor?action=list");

                }

                break;

            default:

                response.sendRedirect("doctor?action=list");

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
        DoctorDAO dao = new DoctorDAO();
         String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {

            //================ INSERT =================
            case "insert":

                try {

                    String fullName = request.getParameter("fullName");
                    String specialization = request.getParameter("specialization");
                    String phone = request.getParameter("phone");
                    int departmentID = Integer.parseInt(request.getParameter("departmentID"));

                    Doctor d = new Doctor();

                    d.setFullName(fullName);
                    d.setSpecialization(specialization);
                    d.setPhone(phone);
                    d.setDepartmentID(departmentID);

                    dao.insert(d);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.sendRedirect("doctor?action=list");

                break;

            //================ UPDATE =================
            case "update":

                try {

                    int doctorID = Integer.parseInt(request.getParameter("doctorID"));

                    String fullName = request.getParameter("fullName");
                    String specialization = request.getParameter("specialization");
                    String phone = request.getParameter("phone");
                    int departmentID = Integer.parseInt(request.getParameter("departmentID"));

                    Doctor d = new Doctor();

                    d.setDoctorID(doctorID);
                    d.setFullName(fullName);
                    d.setSpecialization(specialization);
                    d.setPhone(phone);
                    d.setDepartmentID(departmentID);

                    dao.update(d);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.sendRedirect("doctor?action=list");

                break;

            //================ DELETE =================
            case "delete":

                try {

                    int id = Integer.parseInt(request.getParameter("id"));

                    dao.delete(id);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.sendRedirect("doctor?action=list");

                break;

            default:

                response.sendRedirect("doctor?action=list");

                break;
        }

    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Doctor Servlet";
    }// </editor-fold>

}
