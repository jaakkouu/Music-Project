<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/inc/header.jsp" />  
      
        	<div class="breadcrumb">
        		<a href="${pageContext.request.contextPath}/">All artists</a> /
        		<span><c:out value="${artist.name}" /></span>
        	</div>
        	
        	<h1 class="pageTitle"><c:out value="${artist.name}" /></h1>        	
        	
        	<% if(request.getAttribute("albums") != null) { %>
        	<h3>Albums:</h3>
    		<table class="albums">
				<thead>
					<tr>
						<th>#</th>
						<th align="left">Name</th>
						<th align="left">Number of Songs</th>
						<th></th>
					</tr>
				</thead>
			    <c:forEach items="${albums}" var="album" varStatus="loop">
			        <tr>
			        	<td><c:out value="${loop.count}" />.</td>
			            <td><a href="album/${album.id}"><c:out value="${album.name}" /></a></td>
			            <td><c:out value="${album.songCount}" /></td>
			            <td align="center">   
			            	<button class="btn yellow" onclick="window.location.href = 'album/modify/<c:out value="${album.id}" />';"><i class="fas fa-edit fa-fx"></i> Modify</button>
      						<form style="display: inline-block;" method="post" action="album/delete">
        						<input type="hidden" name="artistId" value="<c:out value="${artist.id}" />" />
        						<input type="hidden" name="albumId" value="<c:out value="${album.id}" />" />
        						<button type="submit" class="btn red"><i class="fas fa-times fa-fx"></i> Remove</button>
      						</form>
      					</td>
			        </tr>
			    </c:forEach>
			</table>
			<% } else { %>
				<p style="margin-bottom: 16px">No albums added yet</p>
			<% } %>
			
        	<form style="margin-top: 20px" method="post" action="album/create">
        		<input type="hidden" name="artistId" value="<c:out value="${artist.id}" />" />
        		<input type="text" placeholder="Type album title" name="title" required>
        		<button type="submit"><i class="fas fa-plus fa-fx"></i> Create</button>
        	</form>
        	
        	<form method="post" action="artist/favorite">
        		<input type="hidden" name="artistId" value="<c:out value="${artist.id}" />" />
        		<button type="submit" class="roundbtn favorite"><i class="far fa-star"></i></button>
        	</form>
        	
<jsp:include page="/WEB-INF/inc/footer.jsp" />  