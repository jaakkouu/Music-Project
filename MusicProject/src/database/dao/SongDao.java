package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ChinookDatabase;
import model.Genre;
import model.MediaType;

public class SongDao {
	
	private ChinookDatabase db = new ChinookDatabase();
	
	public List<Genre> getGenres() {
		Connection conn = db.connect();
        PreparedStatement getGenres = null;
        ResultSet results = null;
        List<Genre> genres = new ArrayList<Genre>();
        try {
    		getGenres = conn.prepareStatement("SELECT * FROM Genre");
    		results = getGenres.executeQuery();
    		while(results.next()) {
    			Genre genre = new Genre(results.getLong("GenreId"), results.getString("Name"));
    			genres.add(genre);
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getGenres, conn);
        }
        return genres;
	}
	
	public List<MediaType> getMediaTypes() {
		Connection conn = db.connect();
        PreparedStatement getMediaTypes = null;
        ResultSet results = null;
        List<MediaType> types = new ArrayList<MediaType>();
        try {
        	getMediaTypes = conn.prepareStatement("SELECT * FROM MediaType");
    		results = getMediaTypes.executeQuery();
    		while(results.next()) {
    			MediaType type = new MediaType(results.getLong("MediaTypeId"), results.getString("Name"));
    			types.add(type);
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getMediaTypes, conn);
        }
        return types;
	}
	
	public long createSong(long albumId, String name, long genreId, long mediaTypeId, long milliSeconds, String unitPrice) {
		Connection conn = db.connect();
        PreparedStatement createSong = null;
        ResultSet keys = null;
        try {
        	 createSong = conn.prepareStatement("INSERT INTO Track (AlbumId, Name, GenreId, MediaTypeId, Milliseconds, UnitPrice) VALUES (?, ?, ?, ?, ?, ?)");
        	 createSong.setLong(1, albumId);
        	 createSong.setString(2, name);
        	 createSong.setLong(3, genreId);
        	 createSong.setLong(4, mediaTypeId);
        	 createSong.setLong(5, milliSeconds);
        	 createSong.setString(6, unitPrice);
        	 createSong.executeUpdate();
        	 keys = createSong.getGeneratedKeys();
        	 keys.next();
     		 return keys.getInt(1);
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(keys, createSong, conn);
        }
        
	}
	
	public boolean removeSong(long songId) {
		Connection conn = db.connect();
        PreparedStatement removeSong = null;
        ResultSet resultSet = null;
        try {
        	removeSong = conn.prepareStatement("DELETE FROM Track WHERE TrackId = ?");
        	removeSong.setLong(1, songId);
        	removeSong.executeUpdate();	
        	return true;
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(resultSet, removeSong, conn);
        }

	}
	
	public String getGenre(long genreId) {
		Connection conn = db.connect();
        PreparedStatement getGenre = null;
        ResultSet results = null;
        try {
        	getGenre = conn.prepareStatement("SELECT Name FROM Genre WHERE GenreId = ?");
        	getGenre.setLong(1, genreId);
        	results = getGenre.executeQuery();	
        	if(results.next()) {
        		return results.getString("Name");
        	} else {
        		return "No Genre Defined";
        	}	
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getGenre, conn);
        }
	}

	public String getMediaType(long mediaTypeId) {
		Connection conn = db.connect();
        PreparedStatement getMediaType = null;
        ResultSet results = null;
        try {
        	getMediaType = conn.prepareStatement("SELECT Name FROM MediaType WHERE MediaTypeId = ?");
        	getMediaType.setLong(1, mediaTypeId);
        	results = getMediaType.executeQuery();	
        	if(results.next()) {
        		return results.getString("Name");
        	} else {
        		return "No MediaType Defined";
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getMediaType, conn);
        }
	}
}

