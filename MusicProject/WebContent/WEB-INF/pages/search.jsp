<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/inc/header.jsp">
	<jsp:param name="pageTitle" value="Search Artists" />
	<jsp:param name="pageSubtitle" value="Search by typing artist's name" />
</jsp:include>   
        	<% if(request.getAttribute("searchValue") != null) { %>

	        	<% if(request.getAttribute("artists") != null) { %>
	        	
	        	<table id="artists" class="artists">
					<thead>
						<tr>
							<th>#</th>
							<th align="left">Name</th>
						</tr>
					</thead>
				    <c:forEach items="${artists}" var="artist" varStatus="loop">
				        <tr <c:if test="${loop.count > 25}">class="hidden"</c:if>>
				        	<td><c:out value="${loop.count}" />.</td>
				            <td><a href="artist/${artist.id}"><c:out value="${artist.name}" /></a></td>
				        </tr>
				    </c:forEach>
				</table>
				
				<p id="loadMore" style="text-align: center; cursor: pointer">Show more</p>
				
				<% } else { %>
				
				<p>No results found</p>
	        	
	        	<% } %>
        	
        	<% } %>
        	
        	<form style="margin-top: 20px" method="GET" action="${pageContext.request.contextPath}/search">
       			<input placeholder="Type artist's name" name="search" type="text">
       			<button type="submit"><i class="fas fa-search fa-fx"></i> Search</button>
        	</form>	
        	
        	<script>
	        	let shownItems = 25,
		        	artists = document.getElementById("artists"),
		        	rows = artists.getElementsByTagName("tr").length,
		        	loadMore = document.getElementById("loadMore");
		
		        loadMore.addEventListener("click", () => {
		        	if(shownItems >= rows) {
		        		loadMore.innerText = "No more results";
		        		return;
		        	}
		        	shownItems = shownItems + 25;
		        	for(let [i,row] of [...artists.rows].entries()) {
		        		if(shownItems >= i) {
		        			row.classList.remove("hidden");
		        		}
		        	}
		        });
        	</script>
  	
<jsp:include page="/WEB-INF/inc/footer.jsp" />    