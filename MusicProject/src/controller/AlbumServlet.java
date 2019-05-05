package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.AlbumDao;
import database.dao.ArtistsDao;
import database.dao.SongDao;
import model.Album;
import model.Genre;
import model.MediaType;
import model.Song;

@WebServlet(name = "AlbumServlet", urlPatterns = "/album/*")

public class AlbumServlet extends HttpServlet {
	
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
		long albumId = Long.parseLong(request.getPathInfo().replace("/", ""));
		
		Album album = albumDao.getAlbum(albumId);
		
		if(album != null) {
			request.setAttribute("album", album);
			List<Song> songs = albumDao.getSongs(albumId);
			
			if(songs.size() > 0) {
				request.setAttribute("songs", songs);
			}	
		}
		
	 	List<Genre> genres = songDao.getGenres();
    	request.setAttribute("genres", genres);
    	
    	List<MediaType> mediaTypes = songDao.getMediaTypes();
    	request.setAttribute("mediaTypes", mediaTypes);
		
		request.getRequestDispatcher("/WEB-INF/pages/album.jsp").forward(request, response);
    }	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		String postAction = request.getPathInfo().replace("/", "");
		
		if (postAction.equals("create")) {
			long artistId = Long.parseLong(request.getParameter("artistId"));
			int albumId = albumDao.createAlbum(artistId, request.getParameter("title"));
			response.sendRedirect(request.getContextPath()+"/album/"+albumId);
		}
		
		if (postAction.equals("delete")) {
			long artistId = Long.parseLong(request.getParameter("artistId"));
			long albumId = Long.parseLong(request.getParameter("albumId"));
			boolean albumExists = artistsDao.albumExists(artistId, albumId);
			if (albumExists) {
				System.out.println("Album found, removing album");
				albumDao.removeAlbum(artistId, albumId);
			}
			response.sendRedirect(request.getContextPath()+"/artist/"+artistId);
		}
	
	}
	

}
