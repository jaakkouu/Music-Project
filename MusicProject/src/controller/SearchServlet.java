package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.ArtistsDao;

import model.Artist;

@WebServlet(name = "SearchServlet", urlPatterns = "/search")

public class SearchServlet extends HttpServlet {
	
	private ArtistsDao dao;

	@Override
    public void init() {
	    dao = new ArtistsDao();
    }
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String searchValue = request.getParameter("search");
		request.setAttribute("searchValue", searchValue);
		
		List<Artist> artists = dao.searchArtists(searchValue);
		
		request.setAttribute("artistsSize", artists.size());
		
		if(artists.size() > 0) {
			request.setAttribute("artists", artists);
		}
		
		request.getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(request, response);
		
    }
	 
  
}
