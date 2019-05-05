<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/inc/header.jsp">
	<jsp:param name="pageTitle" value="My Favorites" />
</jsp:include> 	
        	<% if(session.getAttribute("artists") != null) { %> 
        	<% } else { %>
        		<p>No artists favorited yet</p>
        	<% } %>
<jsp:include page="/WEB-INF/inc/footer.jsp" />    