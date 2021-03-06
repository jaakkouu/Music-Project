<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/inc/header.jsp">
	<jsp:param name="pageTitle" value="Modify" />
	<jsp:param name="pageSubtitle" value="Modify information" />
</jsp:include>      
<form class="gridForm" style="margin-top: 20px" method="post" action="${formActionUrl}">
	<input type="hidden" name="requestedId" value="<c:out value="${requestedId}" />" />
	<c:forEach items="${inputs}" var="input">  
		${input}
	</c:forEach>
	<button type="submit" class="green"><i class="fas fa-check fa-fx"></i> Accept Changes</button>
</form>
<jsp:include page="/WEB-INF/inc/footer.jsp" />    