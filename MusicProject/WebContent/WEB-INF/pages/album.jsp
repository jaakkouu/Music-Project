<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/inc/header.jsp" />  
        	<div class="breadcrumb">
        		<a href="${pageContext.request.contextPath}/">All artists</a> /
        		<a href="#"><c:out value="${artist.name}" /></a> /
        		<span><c:out value="${album.name}" /></span>
        	</div>
        	<h1 class="pageTitle"><c:out value="${album.name}" /></h1>
        	<h2 class="pageSubtitle">Artist name</h2>
      		<% if(request.getAttribute("songs") != null) { %> 
      		<h3>Tracks:</h3>
    		<table class="tracks">
				<thead>
					<tr>
						<th>#</th>
						<th align="left">Name</th>
						<th align="left">Genre</th>
						<th align="left">MediaType</th>
						<th align="left">Length</th>
						<th></th>
					</tr>
				</thead>
			    <c:forEach items="${songs}" var="song" varStatus="loop">
			    	
			    		<tr>
			    	

			        	<td><c:out value="${loop.count}" />.</td>
			            <%--<td><a href="song/${song.id}"><c:out value="${song.name}" /></a></td>--%>
			            <td><c:out value="${song.name}" /></td>
			            <td><c:out value="${song.genre}" /></td>
			            <td><c:out value="${song.mediaType}" /></td>
			            <td><c:out value="${song.songLength}" /></td>
			            <td align="center">
			            	<form style="display: inline-block" method="post" action="song/modify">
       							<input type="hidden" name="songId" value="<c:out value="${song.id}" />" />
       							<input type="hidden" name="albumId" value="<c:out value="${album.id}" />" />
       							<button type="submit" class="btn yellow"><i class="far fa-edit fa-fx"></i> Edit</button>
    						</form>
      						<form style="display: inline-block" method="post" action="song/delete">
        						<input type="hidden" name="songId" value="<c:out value="${song.id}" />" />
        						<input type="hidden" name="albumId" value="<c:out value="${album.id}" />" />
        						<button type="submit" class="btn red"><i class="fas fa-times fa-fx"></i> Remove</button>
      						</form>
     					</td>
			        </tr>
			    </c:forEach>
			</table> 
			
			<% } else { %>
				<p style="margin-bottom: 16px">No songs added yet</p>
			<% } %>
		
			<button id="openNewSong">Add New Song <i class="fas fa-chevron-right fa-fx" style="width: 18px"></i></button>
			
			<div id="songWrapperElement" style="margin-top: 24px; display: none">
			
				<h3>Add song to this album</h3>
			
				<form class="gridForm" method="post" action="song/create">
				
	        		<input type="hidden" name="albumId" value="<c:out value="${album.id}" />" />
	        		
	        		<div>
		        		<label for="title">Name of the song</label>
		        		<input type="text" id="title" placeholder="Type song title" name="title" required>
	        		</div>
	        		
	        		<div>
		        		<label for="genre">Choose song's genre</label>
		        		<select name="genre" id="genre" required>
		        			<option selected disabled>Select Genre...</option>
		        			<c:forEach items="${genres}" var="genre">
		        				<option value="${genre.id}"><c:out value="${genre.name}" /></option>
		        			</c:forEach>
		        		</select>
	        		</div>
	        		
	        		<div>
		        		<label for="mediaTypeId">Choose song's media format</label>
		        		<select name="mediaTypeId" id="mediaTypeId" required>
		        			<option selected disabled>Select Media Type...</option>
		        			<c:forEach items="${mediaTypes}" var="type">
		        				<option value="${type.id}"><c:out value="${type.name}" /></option>
		        			</c:forEach>
		        		</select>
	        		</div>
	        		
	        		<div>
	        			<label for="length">Length of the song in seconds</label>
	        			<input type="text" id="length" placeholder="Length" name="length" required>
	        		</div>
	
	        		<div>        		
		        		<label for="price">Unit price</label>
		        		<input type="text" id="price" placeholder="Unit price" name="price" pattern="\d+(\.\d{2})?" required>
	        		</div>
	        		
	        		<div>
		        		<button type="submit"><i class="fas fa-plus fa-fx"></i> Create</button>        		
	        		</div>
	        		
	        	</form>
        	
        	</div>
        	
        	<script>
			
				$(() => {
					$('#openNewSong').on('click', function() {
						$(this).find("i").toggleClass("fa-chevron-right fa-chevron-down");
						$('#songWrapperElement').slideToggle();
					});
				});
		
			</script>
        	
<jsp:include page="/WEB-INF/inc/footer.jsp" />    