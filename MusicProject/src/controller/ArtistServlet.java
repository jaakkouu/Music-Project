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
import helpers.Breadcrumb;
import model.Album;
import model.Artist;

@WebServlet(name = "ArtistServlet", urlPatterns = "/artist/*")

public class ArtistServlet extends HttpServlet {

	public HttpSession session = null;
	private ArtistsDao artistsDao;
	
    @Override
    public void init() {
    	artistsDao = new ArtistsDao();
    }

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		long artistId = Long.parseLong(request.getPathInfo().replace("/", ""));
		
		Artist artist = artistsDao.getArtist(artistId);

		if(artist != null) {
			
			session = request.getSession();
			List<Long> artistIds = (List<Long>)session.getAttribute("FavoritedArtists");
			if(artistIds.contains(artistId)) {
				request.setAttribute("favorited", "yes");
			} else {
				request.setAttribute("favorited", "no");
			}
			
			if(artist.getAlbumCount() > 0) {
				List<Album> albums = artistsDao.getAlbums(artistId);
				request.setAttribute("albums", albums);
			}
			Breadcrumb breadcrumb = new Breadcrumb();
	    	breadcrumb.setItem("link", "", "All Artists");
	    	breadcrumb.setItem("text", "", artist.getName());
			request.setAttribute("breadcrumb", String.join(" / ", breadcrumb.getBreadcrumb()));
			request.setAttribute("artist", artist);
			request.getRequestDispatcher("/WEB-INF/pages/artist.jsp").forward(request, response);
		}
    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		String postAction = request.getPathInfo().replace("/", "");
		if (postAction.equals("create")) {
			String title = request.getParameter("title");
			int artistId = artistsDao.createArtist(title);
			response.sendRedirect(request.getContextPath()+"/artist/"+artistId);
		}
		if (postAction.equals("favorite")) {
			session = request.getSession();
			List<Long> artistIds = (List<Long>)session.getAttribute("FavoritedArtists");
			if(artistIds == null) {
				artistIds = new ArrayList<Long>();
			}
			long artistId = Long.parseLong(request.getParameter("artistId"));
			Artist artist = artistsDao.getArtist(artistId);
			artistIds.add(artist.getId());
			session.setAttribute("FavoritedArtists", artistIds);
			response.sendRedirect(request.getContextPath()+"/artist/"+artistId);
		}
		if(postAction.equals("unfavorite")) {
			session = request.getSession();
			List<Long> artistIds = (List<Long>)session.getAttribute("FavoritedArtists");
			long artistId = Long.parseLong(request.getParameter("artistId"));
			artistIds.remove(artistId);
			session.setAttribute("FavoritedArtists", artistIds);
			response.sendRedirect(request.getContextPath()+"/artist/"+artistId);
		}
	}

}
