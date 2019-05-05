package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.AlbumDao;
import database.dao.SongDao;
import model.Song;

@WebServlet(name = "SongServlet", urlPatterns = "/song/*")

public class SongServlet extends HttpServlet {

	private SongDao songDao;
	private AlbumDao albumDao;
	
    @Override
    public void init() {
    	songDao = new SongDao();
    	albumDao = new AlbumDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    	long songId = Long.parseLong(request.getPathInfo().replace("/", ""));
    	request.getRequestDispatcher("/WEB-INF/pages/song.jsp").forward(request, response);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		String postAction = request.getPathInfo().replace("/", "");
		
		if(postAction.equals("create")) {
			
			String name = request.getParameter("title"),
					unitPrice = request.getParameter("price");
			
			long albumId = Long.parseLong(request.getParameter("albumId")),
				genreId = Long.parseLong(request.getParameter("genre")),
				mediaTypeId = Long.parseLong(request.getParameter("mediaTypeId")),
				seconds = Long.parseLong(request.getParameter("length")),
				milliSeconds = Song.secondsInMilliseconds(seconds);
			
			songDao.createSong(albumId, name, genreId, mediaTypeId, milliSeconds, unitPrice);
			response.sendRedirect(request.getContextPath()+"/album/"+albumId);
		}
		
		if (postAction.equals("delete")) {
			
			long songId = Long.parseLong(request.getParameter("songId")),
				albumId = Long.parseLong(request.getParameter("albumId"));
		
			boolean songExists = albumDao.songExists(albumId, songId);
			
			if(songExists) {	
				songDao.removeSong(songId);
			} 
			
			response.sendRedirect(request.getContextPath()+"/album/"+albumId);
			
		}
	
	}
	

}
