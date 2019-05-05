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
import model.Album;
import model.Song;

@WebServlet(name = "FavoritesServlet", urlPatterns = "/favorites")

public class FavoritesServlet extends HttpServlet {
	
	public HttpSession session = null;
	
	@Override
    public void init() {
		
    }

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession();
		
		request.getRequestDispatcher("/WEB-INF/pages/favorites.jsp").forward(request, response);
    }

}
