package controller;

import java.io.IOException;

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
import model.Song;

@WebServlet(name = "ModifyServlet", urlPatterns = {"/album/modify/*", "/artist/modify/*"})

public class ModifyServlet extends HttpServlet {
	
	private AlbumDao albumDao;
	private ArtistsDao artistsDao;
	private SongDao songDao;
	
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
		long requestedId = Long.parseLong(request.getPathInfo().substring(1));
		
		if (servletPath.equals("/album/modify")) {			
			Album album = albumDao.getAlbum(requestedId);
			placeholder = "Album Name";
			value = album.getName();
		} else if (servletPath.equals("/artist/modify")) {
			Artist artist = artistsDao.getArtist(requestedId);
			placeholder = "Artist Name";
			value = artist.getName();
		} else if (servletPath.equals("/song/modify")) {
			Song song = songDao.getSong(requestedId);
			placeholder = "Song Name";
			value = song.getName();
		}
		
		request.setAttribute("placeholder", placeholder);
		request.setAttribute("value", value);

		request.getRequestDispatcher("/WEB-INF/pages/modify.jsp").forward(request, response);
    }
	 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String servletPath = request.getServletPath();
    		
		if (servletPath.equals("/album/modify")) {			

		} else if (servletPath.equals("/artist/modify")) {
	
		} else if (servletPath.equals("/song/modify")) {
			
		}
    }
}
