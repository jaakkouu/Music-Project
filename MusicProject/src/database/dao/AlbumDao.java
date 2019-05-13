package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ChinookDatabase;
import model.Album;
import model.Genre;
import model.MediaType;
import model.Song;

public class AlbumDao {
	
	private ChinookDatabase db = new ChinookDatabase();
	
	public Album getAlbum(long albumId) {
		
		Connection conn = db.connect();
        PreparedStatement getAlbum = null;
        ResultSet resultSet = null;
        
        try {
        	getAlbum = conn.prepareStatement("SELECT AlbumId, ArtistId, Title FROM Album WHERE AlbumId = ?");
        	getAlbum.setLong(1, albumId);
        	resultSet = getAlbum.executeQuery();
        	if(resultSet.next()){
        		Album a = new Album(resultSet.getLong("AlbumId"), resultSet.getString("Title"));
        		a.setArtistId(resultSet.getLong("ArtistId"));
        		return a;
        	} else {
        		return null;
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(resultSet, getAlbum, conn);
        }
 
	}
	
	public List<Song> getSongs(long albumId) {
		Connection conn = db.connect();
        PreparedStatement getSongs = null;
        ResultSet results = null;
        List<Song> songs = new ArrayList<Song>();
        SongDao songDao = new SongDao();
        try {
        	getSongs = conn.prepareStatement("SELECT TrackId, Name, MediaTypeId, GenreId, Milliseconds, UnitPrice FROM Track WHERE AlbumId = ?");
        	getSongs.setLong(1, albumId);
        	results = getSongs.executeQuery();
        	while(results.next()) {
        		String name = results.getString("Name"),
    				unitPrice = results.getString("UnitPrice");
        		Genre genre = songDao.getGenre(results.getLong("GenreId"));
        		MediaType mediaType = songDao.getMediaType(results.getLong("MediaTypeId"));
        		long trackId = results.getLong("TrackId"),
    				songLength = results.getLong("Milliseconds");
        		songs.add(new Song(trackId, name, genre, mediaType, songLength, unitPrice));
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getSongs, conn);
        }
		return songs;
	}
	
	public long getSongCount(long albumId) {
		
		Connection conn = db.connect();
        PreparedStatement getSongCount = null;
        ResultSet results = null;

        try {
        	getSongCount = conn.prepareStatement("SELECT count(*) FROM Track WHERE AlbumId = ?");
        	getSongCount.setLong(1, albumId);
			results = getSongCount.executeQuery();
			return results.getLong(1);
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getSongCount, conn);
        }

	}
	
	public void removeAlbum(long artistId, long albumId) {
		
		Connection conn = db.connect();
        PreparedStatement removeAlbum = null;
        ResultSet resultSet = null;
        
        try {
        	removeAlbum = conn.prepareStatement("DELETE FROM Album WHERE AlbumId = ? AND ArtistId = ?");
        	removeAlbum.setLong(1, albumId);
        	removeAlbum.setLong(2, artistId);
        	removeAlbum.executeUpdate();
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(resultSet, removeAlbum, conn);
        }
         
	}
	
	public int createAlbum(long artistId, String albumTitle) {
		
		Connection conn = db.connect();
        PreparedStatement createAlbum = null;
        ResultSet keys = null;
        
        try {
        	createAlbum = conn.prepareStatement("INSERT INTO Album (Title, ArtistId) VALUES (?, ?)");
        	createAlbum.setString(1, albumTitle);
        	createAlbum.setLong(2, artistId);    	
        	createAlbum.executeUpdate();
        	keys = createAlbum.getGeneratedKeys();
        	keys.next();
        	return keys.getInt(1);
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(keys, createAlbum, conn);
        }
		
	}

	// Check if song exists on album
	public boolean songExists(long albumId, long songId) {
		
		Connection conn = db.connect();
        PreparedStatement songExists = null;
        ResultSet resultSet = null;
		
        try {
        	songExists = conn.prepareStatement("SELECT TrackId FROM Track WHERE AlbumId = ? AND TrackId = ?");
        	songExists.setLong(1, albumId);
        	songExists.setLong(2, songId);
        	resultSet = songExists.executeQuery();
        	if(resultSet.next()){
        		return true;
    		} else {
    			return false;
    		}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(resultSet, songExists, conn);
        }
        
	}

	
}
