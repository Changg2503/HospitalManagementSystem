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
import java.util.ArrayList;
import java.util.List;
import model.Patient;
import utils.Validator;

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

        // Xác định Patient hiện tại (nếu role = Patient) để giới hạn dữ liệu
        model.Account account = (model.Account) request.getSession().getAttribute("ACCOUNT");
        Integer myPatientID = null;

        if (account != null && "Patient".equals(account.getRole())) {
            Patient myPatient = dao.getByAccountID(account.getAccountID());
            if (myPatient != null) {
                myPatientID = myPatient.getPatientID();
            }
        }

        switch (action) {

            //================ LIST + PAGINATION =================
            case "list":

                // Role Patient: chỉ xem thông tin của chính mình (không phân trang)
                if (myPatientID != null) {

                    List<Patient> own = new ArrayList<>();
                    Patient me = dao.getById(myPatientID);
                    if (me != null) {
                        own.add(me);
                    }

                    request.setAttribute("listPatient", own);
                    request.setAttribute("currentPage", 1);
                    request.setAttribute("endPage", 1);

                    request.getRequestDispatcher("jsp/patient/list.jsp")
                            .forward(request, response);
                    break;
                }

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

                request.getRequestDispatcher("jsp/patient/list.jsp")
                        .forward(request, response);

                break;

            //================ SEARCH =================
            case "search":

                String keyword = request.getParameter("keyword");

                if (keyword == null) {
                    keyword = "";
                }

                List<Patient> result = dao.search(keyword);

                // Role Patient: chỉ thấy chính mình trong kết quả search (nếu có)
                if (myPatientID != null) {
                    final int pid = myPatientID;
                    result.removeIf(p -> p.getPatientID() != pid);
                }

                request.setAttribute("listPatient", result);
                request.setAttribute("keyword", keyword);

                request.getRequestDispatcher("jsp/patient/list.jsp")
                        .forward(request, response);

                break;

            //================ OPEN ADD FORM =================
            case "add":

                // Role Patient không được tự tạo hồ sơ bệnh nhân mới
                if (myPatientID != null) {
                    response.sendRedirect("patient?action=list");
                    break;
                }

                request.getRequestDispatcher("jsp/patient/form.jsp")
                        .forward(request, response);

                break;

            //================ OPEN EDIT FORM =================
            case "edit":

                try {

                    int id = Integer.parseInt(request.getParameter("id"));

                    // Role Patient: chỉ được sửa hồ sơ của chính mình
                    if (myPatientID != null && id != myPatientID) {
                        response.sendRedirect("patient?action=list");
                        break;
                    }

                    Patient patient = dao.getById(id);

                    request.setAttribute("patient", patient);

                    request.getRequestDispatcher("jsp/patient/form.jsp")
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

        // Xác định Patient hiện tại (nếu role = Patient) để chặn thao tác trên hồ sơ người khác
        model.Account account = (model.Account) request.getSession().getAttribute("ACCOUNT");
        Integer myPatientID = null;

        if (account != null && "Patient".equals(account.getRole())) {
            Patient myPatient = dao.getByAccountID(account.getAccountID());
            if (myPatient != null) {
                myPatientID = myPatient.getPatientID();
            }
        }

        // Role Patient: không được insert hoặc delete
        if (myPatientID != null && ("insert".equals(action) || "delete".equals(action))) {
            request.getSession().setAttribute("FLASH_ERROR", "You are not allowed to perform this action.");
            response.sendRedirect("patient?action=list");
            return;
        }

        // Role Patient: update chỉ được áp dụng cho chính hồ sơ của mình
        if (myPatientID != null && "update".equals(action)) {
            String idParam = request.getParameter("patientID");
            try {
                int targetId = Integer.parseInt(idParam);
                if (targetId != myPatientID) {
                    request.getSession().setAttribute("FLASH_ERROR", "You are not allowed to edit this record.");
                    response.sendRedirect("patient?action=list");
                    return;
                }
            } catch (Exception e) {
                response.sendRedirect("patient?action=list");
                return;
            }
        }

        switch (action) {

            //================ INSERT =================
            case "insert": {

                String accIdParam = request.getParameter("accountID");
                String fullName = request.getParameter("fullName");
                String gender = request.getParameter("gender");
                String dobStr = request.getParameter("dob");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String address = request.getParameter("address");

                List<String> errors = validatePatient(
                        fullName, phone, email, dobStr, 0, dao);

                if (!errors.isEmpty()) {

                    Patient p = new Patient();
                    p.setFullName(fullName);
                    p.setGender(gender);
                    p.setPhone(phone);
                    p.setEmail(email);
                    p.setAddress(address);

                    request.setAttribute("errors", errors);
                    request.setAttribute("patient", p);

                    request.getRequestDispatcher(
                            "jsp/patient/form.jsp")
                            .forward(request, response);
                    return;
                }

                try {

                    int accountID = (accIdParam == null || accIdParam.isEmpty())
                            ? 0 : Integer.parseInt(accIdParam);

                    Date dob = Date.valueOf(dobStr);

                    Patient p = new Patient();

                    p.setAccountID(accountID);
                    p.setFullName(fullName.trim());
                    p.setGender(gender);
                    p.setDob(dob);
                    p.setPhone(phone.trim());
                    p.setEmail(email.trim());
                    p.setAddress(address);

                    dao.insert(p);

                    request.getSession().setAttribute("FLASH_SUCCESS", "Patient added successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("FLASH_ERROR", "Failed to add patient.");
                }

                response.sendRedirect("patient?action=list");

                break;
            }

            //================ UPDATE =================
            case "update": {

                int id = Integer.parseInt(request.getParameter("patientID"));

                String accIdParam = request.getParameter("accountID");
                String fullName = request.getParameter("fullName");
                String gender = request.getParameter("gender");
                String dobStr = request.getParameter("dob");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String address = request.getParameter("address");

                List<String> errors = validatePatient(
                        fullName, phone, email, dobStr, id, dao);

                if (!errors.isEmpty()) {

                    Patient p = new Patient();
                    p.setPatientID(id);
                    p.setFullName(fullName);
                    p.setGender(gender);
                    p.setPhone(phone);
                    p.setEmail(email);
                    p.setAddress(address);

                    request.setAttribute("errors", errors);
                    request.setAttribute("patient", p);

                    request.getRequestDispatcher(
                            "jsp/patient/form.jsp")
                            .forward(request, response);
                    return;
                }

                try {

                    int accountID = (accIdParam == null || accIdParam.isEmpty())
                            ? 0 : Integer.parseInt(accIdParam);

                    Date dob = Date.valueOf(dobStr);

                    Patient p = new Patient();

                    p.setPatientID(id);
                    p.setAccountID(accountID);
                    p.setFullName(fullName.trim());
                    p.setGender(gender);
                    p.setDob(dob);
                    p.setPhone(phone.trim());
                    p.setEmail(email.trim());
                    p.setAddress(address);

                    dao.update(p);

                    request.getSession().setAttribute("FLASH_SUCCESS", "Patient updated successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("FLASH_ERROR", "Failed to update patient.");
                }

                response.sendRedirect("patient?action=list");

                break;
            }

            //================ DELETE =================
            case "delete":

                try {

                    int id = Integer.parseInt(request.getParameter("id"));

                    dao.delete(id);

                    request.getSession().setAttribute("FLASH_SUCCESS", "Patient deleted successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("FLASH_ERROR", "Failed to delete patient.");
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

    // ===============================
    // VALIDATE PATIENT FORM DATA (server-side)
    // excludeId = 0 khi Insert, = PatientID hiện tại khi Update
    // ===============================
    private List<String> validatePatient(
            String fullName, String phone, String email,
            String dobStr, int excludeId, PatientDAO dao) {

        List<String> errors = new ArrayList<>();

        if (!Validator.isRequired(fullName)) {
            errors.add("Full name cannot be empty.");
        }

        if (!Validator.isRequired(phone)) {
            errors.add("Phone number cannot be empty.");
        } else if (!Validator.isValidPhone(phone.trim())) {
            errors.add("Phone number is invalid (must be 10 digits, starting with 0).");
        } else if (dao.existsByPhone(phone.trim(), excludeId)) {
            errors.add("Phone number already exists.");
        }

        if (!Validator.isRequired(email)) {
            errors.add("Email cannot be empty.");
        } else if (!Validator.isValidEmail(email.trim())) {
            errors.add("Email format is invalid.");
        } else if (dao.existsByEmail(email.trim(), excludeId)) {
            errors.add("Email already exists.");
        }

        if (!Validator.isRequired(dobStr)) {
            errors.add("Date of birth cannot be empty.");
        } else if (!Validator.isValidDate(dobStr)) {
            errors.add("Date of birth format is invalid.");
        } else if (!Validator.isNotFutureDate(dobStr)) {
            errors.add("Date of birth cannot be in the future.");
        }

        return errors;
    }

}