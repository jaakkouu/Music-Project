package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.ArtistsDao;
import model.Artist;

@WebServlet(name = "FavoritesServlet", urlPatterns = "/favorites")

public class FavoritesServlet extends HttpServlet {
	
	public HttpSession session = null;
	private ArtistsDao artistsDao;
	
	@Override
	public void init() {
		artistsDao = new ArtistsDao();
	}

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession();
		
		List<Long> artistIds = (List<Long>)session.getAttribute("FavoritedArtists");
		
		if(artistIds.size() > 0) {
			List<Artist> artists = new ArrayList<Artist>();
			
			for(long artistId : artistIds) {
				artists.add(artistsDao.getArtist(artistId));
			}
					
			request.setAttribute("artists", artists);
		}
	
		request.getRequestDispatcher("/WEB-INF/pages/favorites.jsp").forward(request, response);
    }

}
