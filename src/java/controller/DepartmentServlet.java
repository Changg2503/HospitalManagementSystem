/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DepartmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Department;

/**
 *
 * @author admin
 */
@WebServlet(name = "DepartmentServlet", urlPatterns = {"/department"})
public class DepartmentServlet extends HttpServlet {

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
            out.println("<title>Servlet DepartmentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DepartmentServlet at " + request.getContextPath() + "</h1>");
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
        DepartmentDAO dao = new DepartmentDAO();
        String action = request.getParameter("action");

        // Display department list
        if (action == null) {

            request.setAttribute(
                    "list",
                    dao.getAllDepartment());

            request.getRequestDispatcher(
                    "department-list.jsp")
                    .forward(request, response);
        } 
        
        // Open create form
        else if (action.equals("create")) {

            request.getRequestDispatcher(
                    "department-form.jsp")
                    .forward(request, response);
        } 

        // Open update form
        else if (action.equals("edit")) {

            int id = Integer.parseInt( request.getParameter("id"));

            Department d = dao.getDepartmentById(id);

            request.setAttribute("d", d);

            request.getRequestDispatcher(
                    "department-form.jsp")
                    .forward(request, response);
        } 
        
        // Delete department
        else if (action.equals("delete")) {

            int id = Integer.parseInt(
                    request.getParameter("id"));

            dao.deleteDepartment(id);

            response.sendRedirect("department");
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
        DepartmentDAO dao = new DepartmentDAO();

        String id = request.getParameter("id");

        String name = request.getParameter("name");

        // Add new department
        if (id == null || id.isEmpty()) {

            Department d
                    = new Department();

            d.setDepartmentName(name);

            dao.addDepartment(d);
        } // Update department
        else {

            Department d
                    = new Department();

            d.setDepartmentID(
                    Integer.parseInt(id));

            d.setDepartmentName(name);

            dao.updateDepartment(d);
        }

        response.sendRedirect("department");

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
