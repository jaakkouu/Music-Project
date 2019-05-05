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

@WebServlet(name = "HomeServlet", urlPatterns = "")

public class HomeServlet extends HttpServlet {

	private ArtistsDao dao;
	
    @Override
    public void init() {
    	dao = new ArtistsDao();
    }

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Artist> artists = dao.getArtists();
		request.setAttribute("artists", artists);
		request.setAttribute("size", artists.size());
		request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
	 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }
    
}
