<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/inc/header.jsp">
	<jsp:param name="pageTitle" value="My Favorite Artist" />
</jsp:include> 	
        	<% if(request.getAttribute("artists") != null) { %> 
        		<table class="artists" id="artists">
					<thead>
						<tr>
							<th>#</th>
							<th align="left">Name</th>
							<th align="left">Number of Albums</th>
						</tr>
					</thead>
					<tbody>
				    <c:forEach items="${artists}" var="artist" varStatus="loop">
				        <tr <c:if test="${loop.count > 25}">class="hidden"</c:if>>
				        	<td><c:out value="${loop.count}" />.</td>
				            <td><a href="artist/${artist.id}"><c:out value="${artist.name}" /></a></td>
				            <td><c:out value="${artist.albumCount}" /></td>
				        </tr>
				    </c:forEach>
				    </tbody>
				</table>
        	<% } else { %>
        		<p>No artists favorited yet</p>
        	<% } %>
<jsp:include page="/WEB-INF/inc/footer.jsp" />    