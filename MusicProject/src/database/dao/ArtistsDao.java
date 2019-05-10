package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ChinookDatabase;
import model.Album;
import model.Artist;

public class ArtistsDao {
	
	private ChinookDatabase db = new ChinookDatabase();
	
	public int createArtist(String artistName) {
			
			Connection conn = db.connect();
	        PreparedStatement createArtist = null;
	        ResultSet keys = null;
	        
	        try {
	        	createArtist = conn.prepareStatement("INSERT INTO Artist (Name) VALUES (?)");
	        	createArtist.setString(1, artistName);	
	        	createArtist.executeUpdate();
	        	keys = createArtist.getGeneratedKeys();
	        	keys.next();
	        	return keys.getInt(1);
	        } catch (SQLException e) {
	        	throw new RuntimeException(e);
	        } finally {
	        	db.close(keys, createArtist, conn);
	        }
			
	}
	
	public Artist getArtist(long artistId) {
		Connection conn = db.connect();
		PreparedStatement getArtist = null;
		ResultSet results = null;
		try {
			getArtist = conn.prepareStatement("SELECT * FROM Artist WHERE ArtistId = ?");
			getArtist.setLong(1, artistId);
			results = getArtist.executeQuery();
			if(results.next()) {
				Artist a = new Artist(results.getLong("ArtistId"), results.getString("Name"));
				a.setAlbumCount(getAlbumCount(artistId));
				return a;
			} else {
				return null;
			}
		} catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getArtist, conn);
        }
	}
		
	public int getAlbumCount(long artistId) {
		Connection conn = db.connect();
		PreparedStatement getAlbumCount = null;
		ResultSet results = null;
		try {
			getAlbumCount = conn.prepareStatement("SELECT count(*) FROM Album WHERE ArtistId = ?");
			getAlbumCount.setLong(1, artistId);
			results = getAlbumCount.executeQuery();
			return results.getInt(1);
		} catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getAlbumCount, conn);
        }
	}
	
	public List<Artist> getArtists() {
		Connection conn = db.connect();
        PreparedStatement getAllArtists = null;
        ResultSet results = null;
        List<Artist> artists = new ArrayList<>();
        try {
        	getAllArtists = conn.prepareStatement("SELECT * FROM Artist ORDER BY Name");
        	results = getAllArtists.executeQuery();
        	while(results.next()) {
        		long artistId = results.getLong("ArtistId");
        		Artist a = new Artist(artistId, results.getString("Name"));
        		a.setAlbumCount(getAlbumCount(artistId));
        		artists.add(a);
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getAllArtists, conn);
        }
        return artists;
       
	}
	
	public List<Artist> searchArtists(String searchString) {
		Connection conn = db.connect();
        PreparedStatement searchArtists = null;
        ResultSet results = null;
        List<Artist> artists = new ArrayList<>();
        try {
        	searchArtists = conn.prepareStatement("SELECT ArtistId, Name FROM Artist WHERE Name LIKE ?");
        	searchArtists.setString(1, "%"+searchString+"%");
        	results = searchArtists.executeQuery();
        	while(results.next()) {
        		artists.add(new Artist(results.getLong("ArtistId"), results.getString("Name")));
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, searchArtists, conn);
        }
        return artists;
	}

	public List<Album> getAlbums(long artistId) {
		Connection conn = db.connect();
        PreparedStatement getAlbums = null;
        ResultSet results = null;
        List<Album> albums = new ArrayList<>();
        try {
        	getAlbums = conn.prepareStatement("SELECT * FROM Album WHERE ArtistId = ?");
        	getAlbums.setLong(1, artistId);
        	results = getAlbums.executeQuery();
        	AlbumDao albumDao = new AlbumDao();
        	while(results.next()) {
        		long albumId = results.getLong("AlbumId"),
        			songCount = albumDao.getSongCount(albumId);
        		Album a = new Album(albumId, results.getString("Title"));
        		a.setSongCount(songCount);
        		albums.add(a);
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getAlbums, conn);
        }
        return albums;
	}
	
	// Check if album exists on artist
	public boolean albumExists(long artistId, long albumId) {
		
		Connection conn = db.connect();
        PreparedStatement albumExists = null;
        ResultSet resultSet = null;
		
        try {
        	albumExists = conn.prepareStatement("SELECT AlbumId FROM Album WHERE AlbumId = ? AND ArtistId = ?");
        	albumExists.setLong(1, albumId);
        	albumExists.setLong(2, artistId);
        	resultSet = albumExists.executeQuery();
        	if(resultSet.next()){
        		return true;
    		} else {
    			return false;
    		}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(resultSet, albumExists, conn);
        }
        
	}
	/*
	public boolean favoriteArtist(long artistId) {
		
		
		
		
		
		Connection conn = db.connect();
        PreparedStatement favoriteArtist = null;
        ResultSet resultSet = null;
		
        try {
        	favoriteArtist = conn.prepareStatement("SELECT Title FROM Album WHERE ArtistId = ?");
        	favoriteArtist.setLong(1, artistId);
        	resultSet = favoriteArtist.executeQuery();
        	if(resultSet.next()){
        		String title = resultSet.getString("Title");
        		Artist a = new Artist(artistId, title);
        		a.setAlbumCount(getAlbumCount(artistId));
        		
    		} else {
    			
    		}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(resultSet, favoriteArtist, conn);
        }
        
        
		
	}
	*/
}
