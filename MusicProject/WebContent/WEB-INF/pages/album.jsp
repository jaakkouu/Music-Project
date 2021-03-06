<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/inc/header.jsp">
	<jsp:param name="pageTitle" value="${album.name}" />
	<jsp:param name="pageSubtitle" value="by ${artist}" />
</jsp:include>
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
						<th align="left">Unit Price</th>
						<th></th>
					</tr>
				</thead>
			    <c:forEach items="${songs}" var="song" varStatus="loop">
			    		<tr>
			        	<td><c:out value="${loop.count}" />.</td>
			            <td><c:out value="${song.name}" /></td>
			            <td><c:out value="${song.genre.name}" /></td>
			            <td><c:out value="${song.mediaType.name}" /></td>
			            <td><c:out value="${song.songLength}" /></td>
			            <td><c:out value="${song.unitPrice}" />$</td>
			            <td align="center">
			            	<button class="btn yellow" onclick="window.location.href = 'song/modify/<c:out value="${song.id}" />';"><i class="fas fa-edit fa-fx"></i> Modify</button>
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
		
			<button id="openNewSong">Add New Song</button>
			
			<div id="songWrapperElement">
			
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
        	
        		let newSong = document.getElementById("openNewSong"),
        			songWrapperElement = document.getElementById("songWrapperElement");
        		
        		newSong.addEventListener("click", (e) => {
        			
        			if(songWrapperElement.classList) {
        				songWrapperElement.classList.toggle("show");
        			}
        				
        		});

			</script>
        	
<jsp:include page="/WEB-INF/inc/footer.jsp" />    