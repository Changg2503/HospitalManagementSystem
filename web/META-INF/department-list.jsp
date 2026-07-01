<%-- 
    Document   : department-list
    Created on : Jul 1, 2026, 8:30:45 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Department</title>
    </head>
    <body>
        <h2>Department Management</h2>

        <a href="${pageContext.request.contextPath}/department/create">
            Add Department
        </a>

        <br><br>

        <table border="1">

            <tr>
                <th>ID</th>
                <th>Department Name</th>
                <th>Action</th>
            </tr>

            <c:forEach items="${list}" var="d">

                <tr>

                    <td>${d.departmentID}</td>

                    <td>${d.departmentName}</td>

                    <td>

                        <a href="${pageContext.request.contextPath}/department/update?id=${d.departmentID}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/department/delete?id=${d.departmentID}"
                           onclick="return confirm('Delete this department?')">
                            Delete
                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </body>
</html>
