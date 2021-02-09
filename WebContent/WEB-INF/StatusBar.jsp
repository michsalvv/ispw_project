<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.utilities.Role" %>
<%@ page import="logic.controller.AcceptRequestController" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="logic.exceptions.RecordNotFoundException" %>

	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="res/style/StatusBar.css">
	</head>

<%	
	UserBean user = (UserBean) request.getSession().getAttribute("loggedUser");

	int reqCount = 0;
	AcceptRequestController acceptRequestController = new AcceptRequestController();
	try {
		reqCount = acceptRequestController.getRequests(user).size();
		
	} catch (SQLException e) {
		request.setAttribute("alertMsg", "An error as occured. Try later.");
		request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
		return;
		
	} catch (RecordNotFoundException e) {
		reqCount = 0;
		
	} finally {
		request.setAttribute("reqCount", reqCount);
	}
%>
	
	<body>
		<!-- Status bar -->
		<div class="topnav">
			<table align="right">
			  <tbody>
				<tr>
				  	<td style="padding-right: 10px;">
						<label class="status-label" id="statusName">
							<%=user.getName()%>
						</label>
					</td>
					
				  	<td style="padding-right: 10px;">
				  		<%if (user.getRole() == Role.PROFESSOR) { %>
				  		<a href="/ispw_project/RequestPageServlet" class="nav-button">
				  			<button class="status-button" type="button" name="notification">
				  			<% reqCount = (int)request.getAttribute("reqCount"); %>
				  			<%if ( reqCount > 0) { %>
					  			<svg width="24" height="26" viewBox="0 0 24 26" stroke="#FF00FF" xmlns="http://www.w3.org/2000/svg">
									<path d="M23.0209 11.6458C22.5724 11.6458 22.2084 11.2818 22.2084 10.8333C22.2084 7.72192 20.9974 4.79803 18.7981 2.59778C18.4807 2.2804 18.4807 1.76584 18.7981 1.44846C19.1155 1.13107 19.63 1.13107 19.9476 1.44846C22.4534 3.95519 23.8334 7.2887 23.8334 10.8333C23.8334 11.2818 23.4694 11.6458 23.0209 11.6458Z" fill="#0C0B0B"/>
									<path d="M0.8125 11.6458C0.363998 11.6458 0 11.2818 0 10.8333C0 7.28868 1.38022 3.95517 3.88695 1.44943C4.20433 1.13205 4.71909 1.13205 5.03647 1.44943C5.35385 1.76682 5.35385 2.28157 5.03647 2.59895C2.83621 4.79802 1.625 7.72191 1.625 10.8333C1.625 11.2818 1.261 11.6458 0.8125 11.6458Z" fill="#0C0B0B"/>
									<path d="M11.9165 26C9.67618 26 7.854 24.1778 7.854 21.9375C7.854 21.489 8.218 21.125 8.6665 21.125C9.11501 21.125 9.479 21.489 9.479 21.9375C9.479 23.282 10.572 24.375 11.9165 24.375C13.2608 24.375 14.354 23.282 14.354 21.9375C14.354 21.489 14.718 21.125 15.1665 21.125C15.615 21.125 15.979 21.489 15.979 21.9375C15.979 24.1778 14.1568 26 11.9165 26Z" fill="#0C0B0B"/>
									<path d="M20.8542 22.75H2.97922C1.93364 22.75 1.08325 21.8996 1.08325 20.8542C1.08325 20.2994 1.32486 19.7741 1.74638 19.4133C3.394 18.0212 4.33325 15.9977 4.33325 13.8538V10.8333C4.33325 6.65175 7.735 3.25 11.9167 3.25C16.0982 3.25 19.5 6.65175 19.5 10.8333V13.8538C19.5 15.9977 20.4392 18.0212 22.0761 19.4058C22.5084 19.7741 22.75 20.2994 22.75 20.8542C22.75 21.8996 21.8996 22.75 20.8542 22.75ZM11.9167 4.875C8.63081 4.875 5.95825 7.54756 5.95825 10.8333V13.8538C5.95825 16.4763 4.80893 18.9529 2.80585 20.6461C2.76796 20.6787 2.70825 20.7459 2.70825 20.8542C2.70825 21.0014 2.83183 21.125 2.97922 21.125H20.8542C21.0014 21.125 21.125 21.0014 21.125 20.8542C21.125 20.7459 21.0655 20.6787 21.0296 20.6483C19.0243 18.9529 17.875 16.4763 17.875 13.8538V10.8333C17.875 7.54756 15.2024 4.875 11.9167 4.875Z" fill="#0C0B0B"/>
									<path d="M11.9165 4.875C11.468 4.875 11.104 4.511 11.104 4.0625V0.8125C11.104 0.363998 11.468 0 11.9165 0C12.365 0 12.729 0.363998 12.729 0.8125V4.0625C12.729 4.511 12.365 4.875 11.9165 4.875Z" fill="#0C0B0B"/>
									<text x="7" y="18" class="small">${reqCount}</text>
								</svg>
							<%} else { %>
								<svg width="24" height="26" viewBox="0 0 24 26" stroke="none" xmlns="http://www.w3.org/2000/svg">
									<path d="M23.0209 11.6458C22.5724 11.6458 22.2084 11.2818 22.2084 10.8333C22.2084 7.72192 20.9974 4.79803 18.7981 2.59778C18.4807 2.2804 18.4807 1.76584 18.7981 1.44846C19.1155 1.13107 19.63 1.13107 19.9476 1.44846C22.4534 3.95519 23.8334 7.2887 23.8334 10.8333C23.8334 11.2818 23.4694 11.6458 23.0209 11.6458Z" fill="#0C0B0B"/>
									<path d="M0.8125 11.6458C0.363998 11.6458 0 11.2818 0 10.8333C0 7.28868 1.38022 3.95517 3.88695 1.44943C4.20433 1.13205 4.71909 1.13205 5.03647 1.44943C5.35385 1.76682 5.35385 2.28157 5.03647 2.59895C2.83621 4.79802 1.625 7.72191 1.625 10.8333C1.625 11.2818 1.261 11.6458 0.8125 11.6458Z" fill="#0C0B0B"/>
									<path d="M11.9165 26C9.67618 26 7.854 24.1778 7.854 21.9375C7.854 21.489 8.218 21.125 8.6665 21.125C9.11501 21.125 9.479 21.489 9.479 21.9375C9.479 23.282 10.572 24.375 11.9165 24.375C13.2608 24.375 14.354 23.282 14.354 21.9375C14.354 21.489 14.718 21.125 15.1665 21.125C15.615 21.125 15.979 21.489 15.979 21.9375C15.979 24.1778 14.1568 26 11.9165 26Z" fill="#0C0B0B"/>
									<path d="M20.8542 22.75H2.97922C1.93364 22.75 1.08325 21.8996 1.08325 20.8542C1.08325 20.2994 1.32486 19.7741 1.74638 19.4133C3.394 18.0212 4.33325 15.9977 4.33325 13.8538V10.8333C4.33325 6.65175 7.735 3.25 11.9167 3.25C16.0982 3.25 19.5 6.65175 19.5 10.8333V13.8538C19.5 15.9977 20.4392 18.0212 22.0761 19.4058C22.5084 19.7741 22.75 20.2994 22.75 20.8542C22.75 21.8996 21.8996 22.75 20.8542 22.75ZM11.9167 4.875C8.63081 4.875 5.95825 7.54756 5.95825 10.8333V13.8538C5.95825 16.4763 4.80893 18.9529 2.80585 20.6461C2.76796 20.6787 2.70825 20.7459 2.70825 20.8542C2.70825 21.0014 2.83183 21.125 2.97922 21.125H20.8542C21.0014 21.125 21.125 21.0014 21.125 20.8542C21.125 20.7459 21.0655 20.6787 21.0296 20.6483C19.0243 18.9529 17.875 16.4763 17.875 13.8538V10.8333C17.875 7.54756 15.2024 4.875 11.9167 4.875Z" fill="#0C0B0B"/>
									<path d="M11.9165 4.875C11.468 4.875 11.104 4.511 11.104 4.0625V0.8125C11.104 0.363998 11.468 0 11.9165 0C12.365 0 12.729 0.363998 12.729 0.8125V4.0625C12.729 4.511 12.365 4.875 11.9165 4.875Z" fill="#0C0B0B"/>
								</svg>
							<%} %>
					  		</button>
				  		</a>
				  		<%} %>
					</td>
					
				  	<td style="padding-right: 10px;">
						<input class="status-avatar" type="image" src="res/img/Logo.png" alt="avatar"/>
					</td>
					
				  	<td style="padding-right: 10px;">
				  		<a href="/ispw_project/LoginServlet" onclick="session.invalidate();">
						<button class="status-button" type="button" name="logout">
							<svg width="27" height="27" viewBox="0 0 27 27" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M18.4067 19.9873V22.0269C18.4067 24.2761 16.5769 26.1059 14.3277 26.1059H4.07905C1.8298 26.1059 0 24.2761 0 22.0269V4.07905C0 1.8298 1.8298 0 4.07905 0H14.3277C16.5769 0 18.4067 1.8298 18.4067 4.07905V6.11857C18.4067 6.68183 17.9502 7.13833 17.3869 7.13833C16.8237 7.13833 16.3672 6.68183 16.3672 6.11857V4.07905C16.3672 2.95452 15.4522 2.03952 14.3277 2.03952H4.07905C2.95452 2.03952 2.03952 2.95452 2.03952 4.07905V22.0269C2.03952 23.1514 2.95452 24.0664 4.07905 24.0664H14.3277C15.4522 24.0664 16.3672 23.1514 16.3672 22.0269V19.9873C16.3672 19.4241 16.8237 18.9676 17.3869 18.9676C17.9502 18.9676 18.4067 19.4241 18.4067 19.9873ZM25.3592 11.3012L23.0757 9.01772C22.6774 8.61938 22.0316 8.61938 21.6335 9.01772C21.2351 9.41587 21.2351 10.0616 21.6335 10.4597L23.2577 12.0842H11.0134C10.4502 12.0842 9.99367 12.5407 9.99367 13.1039C9.99367 13.6672 10.4502 14.1237 11.0134 14.1237H23.2577L21.6335 15.7482C21.2351 16.1463 21.2351 16.792 21.6335 17.1902C21.8327 17.3893 22.0936 17.4889 22.3545 17.4889C22.6156 17.4889 22.8765 17.3893 23.0757 17.1902L25.3592 14.9066C26.3533 13.9126 26.3533 12.2953 25.3592 11.3012Z" fill="black"/>
							</svg>
						</button>
						</a>
					</td>
					
				</tr>
			  </tbody>
			</table>
		</div>
	</body>
</html>