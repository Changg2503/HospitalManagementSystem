/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.AccountDAO;
import dao.PatientDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Date;
import model.Account;
import model.Patient;

/**
 *
 * @author Nguyen Trang
 */
public class RegisterServlet extends HttpServlet {
   
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
            out.println("<title>Servlet RegisterServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath () + "</h1>");
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
        request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
                
               
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

        try {

            String username =
                    request.getParameter("username");

            String password =
                    request.getParameter("password");

            String fullName =
                    request.getParameter("fullName");

            String gender =
                    request.getParameter("gender");

            String dobStr =
                    request.getParameter("dob");

            String phone =
                    request.getParameter("phone");

            String email =
                    request.getParameter("email");

            String address =
                    request.getParameter("address");

            Date dob = null;

            if (dobStr != null
                    && !dobStr.isEmpty()) {
                dob = Date.valueOf(dobStr);
            }

            AccountDAO accountDAO =
                    new AccountDAO();

            // kiểm tra username tồn tại
            if (accountDAO
                    .checkUsernameExist(username)) {

                request.setAttribute(
                        "ERROR",
                        "Username already exists!");

                request.getRequestDispatcher(
                        "jsp/register.jsp")
                        .forward(request,
                                response);

                return;
            }

            // Tạo Account
            Account acc =
                    new Account();

            acc.setUsername(username);
            acc.setPassword(password);
            acc.setRole("Patient");
            acc.setStatus(true);

            accountDAO.createAccount(acc);

            // Lấy AccountID vừa tạo
            int accountID =
                    accountDAO
                            .getAccountIDByUsername(
                                    username);

            // Tạo Patient
            Patient p =
                    new Patient();

            p.setAccountID(accountID);
            p.setFullName(fullName);
            p.setGender(gender);
            p.setDob(dob);
            p.setPhone(phone);
            p.setEmail(email);
            p.setAddress(address);

            PatientDAO patientDAO =
                    new PatientDAO();

            patientDAO.insert(p);

            request.setAttribute(
                    "SUCCESS",
                    "Register successfully!");

            request.getRequestDispatcher(
                    "jsp/login.jsp")
                    .forward(request,
                            response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "ERROR",
                    "Register failed!");

            request.getRequestDispatcher(
                    "jsp/register.jsp")
                    .forward(request,
                            response);
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
