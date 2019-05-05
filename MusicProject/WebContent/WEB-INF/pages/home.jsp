<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/inc/header.jsp">
	<jsp:param name="pageTitle" value="Music Catalog" />
	<jsp:param name="pageSubtitle" value="All ${size} artists:" />
</jsp:include>    

			<table class="artists">
				<thead>
					<tr>
						<th>#</th>
						<th align="left">Name</th>
						<th align="left">Number of Albums</th>
					</tr>
				</thead>
				<tbody>
			    <c:forEach items="${artists}" var="artist" varStatus="loop">
			        <tr <c:if test="${loop.count >= 10}">style="display: none"</c:if>>
			        	<td><c:out value="${loop.count}" />.</td>
			            <td><a href="artist/${artist.id}"><c:out value="${artist.name}" /></a></td>
			            <td><c:out value="${artist.albumCount}" /></td>
			        </tr>
			    </c:forEach>
			    </tbody>
			</table>
		
       		<form style="margin-top: 20px" method="post" action="artist/create">
        		<input type="hidden" name="artistId" value="<c:out value="${artist.id}" />" />
        		<input type="type" placeholder="Type artist name" name="title" required>
        		<button type="submit"><i class="fas fa-plus fa-fx"></i> Create</button>
        	</form>
        
<jsp:include page="/WEB-INF/inc/footer.jsp" />    