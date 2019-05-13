package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.AlbumDao;
import database.dao.ArtistsDao;
import database.dao.SongDao;
import model.Album;
import model.Artist;
import model.Genre;
import model.MediaType;
import model.Song;

@WebServlet(name = "ModifyServlet", urlPatterns = {"/album/modify/*", "/artist/modify/*", "/song/modify/*"})

public class ModifyServlet extends HttpServlet {
	
	private AlbumDao albumDao;
	private ArtistsDao artistsDao;
	private SongDao songDao;
	private long requestedId;
	
    @Override
    public void init() {
    	albumDao = new AlbumDao();
    	artistsDao = new ArtistsDao();
    	songDao = new SongDao();
    }

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String servletPath = request.getServletPath(),
			placeholder = null,
			formActionUrl = null,
			value = null;

		List<Object> inputs = new ArrayList<>();
		
		requestedId = Long.parseLong(request.getPathInfo().substring(1));
		
		if (servletPath.equals("/album/modify")) {	
			formActionUrl = "album/modify";
			inputs = getInputs("album");
		} else if (servletPath.equals("/artist/modify")) {
			formActionUrl = "artist/modify";
			inputs = getInputs("artist");
		} else if (servletPath.equals("/song/modify")) {
			formActionUrl = "song/modify";
			inputs = getInputs("song");
		}
		
		request.setAttribute("requestedId", requestedId);
		request.setAttribute("formActionUrl", formActionUrl);
		request.setAttribute("placeholder", placeholder);
		request.setAttribute("value", value);
		request.setAttribute("inputs", inputs);
		
		request.getRequestDispatcher("/WEB-INF/pages/modify.jsp").forward(request, response);
    }
	 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String servletPath = request.getServletPath();
    		
		if (servletPath.equals("/album/modify")) {			

		} else if (servletPath.equals("/artist/modify")) {
	
		} else if (servletPath.equals("/song/modify")) {
			
			String name = request.getParameter("title"),
					unitPrice = request.getParameter("price"),
					seconds = request.getParameter("length");

			long songId = Long.parseLong(request.getParameter("requestedId")),
				genreId = Long.parseLong(request.getParameter("genre")),
				mediaTypeId = Long.parseLong(request.getParameter("mediatype"));
			
			songId = songDao.modifySong(songId, name, genreId, mediaTypeId, seconds, unitPrice);
			
			// Redirect doesnt work properly
			response.sendRedirect(request.getContextPath()+"/song/"+songId);
		}
    }
    
    private List<Object> getInputs(String requestedForm) {
    	
    	class Attributes {
    		private boolean required = true;
    		private String type = "", 
    					name = "",
    					id = "",
    					initialValue = "",
    					placeholder = "",
    					labelText,
    					pattern;
    		
    		public Attributes(String type, String name, String id, String initialValue, String placeholder, String labelText) {
    			if(type != "") {
    				setType(type);
    			}
    			setName(name);
    			setId(id);
    			setInitialValue(initialValue);
    			setPlaceholder(placeholder);
    			if(labelText != "") {
    				setLabelText(labelText);
    			}
    		}
    		
    		public void setType(String type) {
    			this.type = type;
    		}
    		
    		public String getType() {
    			return this.type;
    		}
    		
    		public void setInitialValue(String initialValue) {
    			this.initialValue = initialValue;
    		}
    		
    		public String getInitialValue() {
    			return this.initialValue;
    		}
    		
    		public void setName(String name) {
    			this.name = name;
    		}
    		
    		public void setId(String id) {
    			this.id = id;
    		}
    		
			public void setPlaceholder(String placeholder) {
				this.placeholder = placeholder;
			}
			
			public String getName() {
				return this.name;
			}
			
			public String getId() {
				return this.id;
			}
			
			public String getPlaceholder() {
				return this.placeholder;
			}
			
			public String getLabelText() {
				return this.labelText;
			}
			
			public void setLabelText(String labelText) {
				this.labelText = labelText;
			}
			
			public String getPattern() {
				return this.pattern;
			}
			
			public void setPattern(String pattern) {
				this.pattern = pattern;
			}
			
			public void toggleRequired() {
				this.required = !this.required;
			}
			
			public boolean getRequired() {
				return this.required;
			}
			
    	}
    	
    	class Input extends Attributes {
    		
    		public Input(String type, String name, String id, String initialValue, String placeholder,
					String labelText) {
				super(type, name, id, initialValue, placeholder, labelText);
			}

			@Override
    		public String toString() {
    			String html = "<div>";
    			html += getLabelText() != null ? "<label for='"+getId()+"'>" + getLabelText() + "</label>" : "";
    			html += "<input";
				html += getType() != null ? " type='" + getType() + "'" : "";
				html += getName() != null ? " name='" + getName() + "'" : "";
				html += getId() != null ? " id='" + getId() + "'" : "";
				html += getInitialValue() != null ? " value='" + getInitialValue() + "'" : "";
				html += getPlaceholder() != null ? " placeholder='" + getPlaceholder() + "'" : "";
				html += getPattern() != null ? " pattern='" + getPattern() + "'" : "";
				html += getRequired() ? " required" : "";
				html += ">";
				html += "</div>";
				
    			return html;
    		}
    	}
    	
    	class Select extends Attributes {
	
    		public Select(String name, String id, String initialValue, String placeholder,
					String labelText) {
				super("", name, id, initialValue, placeholder, labelText);
			}
    		
    		@Override
			public String toString() {
    			String html = "<div>";
    			html += getLabelText() != "" ? "<label for='"+getId()+"'>" + getLabelText() + "</label>" : "";
    			html += "<select name='"+getName()+"' id='"+getId()+"'>";   
    					
				if(getName().equals("mediatype")) {
			    	List<MediaType> mediaTypes = songDao.getMediaTypes();
			    	for(MediaType type : mediaTypes) {
						html += "<option value='"+type.getId()+"'";
						html += type.getName().equals(this.getInitialValue()) ? " selected>" : ">"; 
						html += type.getName(); 
						html += "</option>";
					}
				} else if (getName().equals("genre")) {
					List<Genre> genres = songDao.getGenres();
					for(Genre genre : genres) {
						html += "<option value='"+genre.getId()+"'";
						html += genre.getName().equals(this.getInitialValue()) ? " selected>" : ">"; 
						html += genre.getName(); 
						html += "</option>";
					}
				}
				
				html += "</select>";
				html += "</div>";
				return html;
    		}
     	}
    	
    	List<Object> inputs = new ArrayList<>();
    	    	
    	switch(requestedForm) {
    	
    		case "album":
    			Album album = albumDao.getAlbum(requestedId);
    	    	inputs.add(new Input("text", "title", "title", album.getName(), "Type a new album name", "Album Name"));
    			break;
    		case "artist":
    			Artist artist = artistsDao.getArtist(requestedId);
    			inputs.add(new Input("text", "title", "title", artist.getName(), "Type a new artist name", "Artist Name"));
    			break;
    		case "song":
    			Song song = songDao.getSong(requestedId);
    			Input name = new Input("text", "title", "title", song.getName(), "Type a new song name", "Song Name");
    			Select genre = new Select("genre", "genre", song.getGenre().getName(), "Select a new genre name", "Genre");
    			Select mediaType = new Select("mediatype", "mediatype", song.getMediaType().getName(), "Select a new media type", "Media Format");
    			Input length = new Input("text", "length", "length", song.getSongLengthInSeconds(), "Type a new length it seconds", "Length In Seconds");
    			Input unitPrice = new Input("text", "price", "price", song.getUnitPrice(), "Type a new unit price (#.##)", "Unit Price");
    			
    			unitPrice.setPattern("\\d+(\\.\\d{2})?");
    			
    			inputs.add(name);
				inputs.add(genre);
				inputs.add(mediaType);
				inputs.add(length);
				inputs.add(unitPrice);
    			
    			break;
    	}
    	
    	return inputs;
    }
}
