package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.ChinookDatabase;
import model.Genre;
import model.MediaType;
import model.Song;

public class SongDao {
	
	private ChinookDatabase db = new ChinookDatabase();
	
	public Song getSong(long songId) {
		
		Connection conn = db.connect();
        PreparedStatement getSong = null;
        ResultSet results = null;
        
        try {
        	getSong = conn.prepareStatement("SELECT TrackId, Name, MediaTypeId, GenreId, Milliseconds, UnitPrice, AlbumId FROM Track WHERE TrackId = ?");
        	getSong.setLong(1, songId);
        	results = getSong.executeQuery();
        	if(results.next()){
        		String name = results.getString("Name"),
    				unitPrice = results.getString("UnitPrice");
            	Genre genre = getGenre(results.getLong("GenreId"));
            	MediaType mediaType = getMediaType(results.getLong("MediaTypeId"));
        		long trackId = results.getLong("TrackId"),
    				songLength = results.getLong("Milliseconds"),
    				albumId = results.getLong("AlbumId");
        		Song a = new Song(trackId, name, genre, mediaType, songLength, unitPrice);
        		a.setAlbumId(albumId);
        		return a;
        	} else {
        		return null;
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getSong, conn);
        }
 
	}
	
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
	
	
	// Tajusin liian myöhään, että tässä olisi voinut hyödyntää stmt.setObject
	public long modifySong(long songId, String name, long genreId, long mediaTypeId, long seconds, String unitPrice) {
		
		Connection conn = db.connect();
        PreparedStatement modifySong = null;
        ResultSet keys = null;
        
        // Get current song
        Song song = getSong(songId);
        
        // Update only if changed data is different
        String sql = "UPDATE Track SET";
        
        Map<String, List<String>> parameters = new HashMap<>();
        
        if(!song.getName().equals(name)) {
        	List<String> a = new ArrayList<>();
        	a.add("Name");
        	a.add(name);
        	parameters.put("String", a);
        }
        
        if(song.getGenre().getId() != genreId) {
        	List<String> a = new ArrayList<>();
        	a.add("GenreId");
        	a.add(Long.toString(genreId));
        	parameters.put("Long", a);
        }
        
        if(song.getMediaType().getId() != mediaTypeId) {
        	List<String> a = new ArrayList<>();
        	a.add("MediaTypeId");
        	a.add(Long.toString(mediaTypeId));
        	parameters.put("Long", a);
        }
        
        if(song.getSongLengthInSeconds() != seconds) {
        	String milliSeconds = Long.toString(song.secondsInMilliseconds(seconds));
        	List<String> a = new ArrayList<>();
        	a.add("MilliSeconds");
        	a.add(milliSeconds);
        	parameters.put("Long", a);
        }
        
        if(!song.getUnitPrice().equals(unitPrice)) {
        	List<String> a = new ArrayList<>();
        	a.add("UnitPrice");
        	a.add(unitPrice);
        	parameters.put("String", a);
        }
        
        for (Map.Entry<String, List<String>> p : parameters.entrySet()) {	
        	sql = sql + " " + p.getValue().get(0) + " = ?,";
        }
        
        if(sql.endsWith(",")){
          sql = sql.substring(0, sql.length() - 1);
        }
        
        sql += " WHERE TrackId = ?";
        
        // If no changes
        if(parameters.size() == 0) {
        	System.out.println("No changes made");
        	return song.getAlbumId();
        }
        
        try {
        	modifySong = conn.prepareStatement(sql); 
            int position = 1;
            for (Map.Entry<String, List<String>> p : parameters.entrySet()) {	
            	if(p.getKey().equals("String")) {
            		modifySong.setString(position, p.getValue().get(1));
            	} else if (p.getKey().equals("Long")) {
            		modifySong.setLong(position, Long.parseLong(p.getValue().get(1)));
            	}
            	position++;
            }
            modifySong.setLong(position, songId);
            modifySong.executeUpdate();
     		return song.getAlbumId();
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(keys, modifySong, conn);
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
	
	public Genre getGenre(long genreId) {
		Connection conn = db.connect();
        PreparedStatement getGenre = null;
        ResultSet results = null;
        try {
        	getGenre = conn.prepareStatement("SELECT Name FROM Genre WHERE GenreId = ?");
        	getGenre.setLong(1, genreId);
        	results = getGenre.executeQuery();	
        	if(results.next()) {
        		return new Genre(genreId, results.getString("Name"));
        	} else {
        		return null;
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getGenre, conn);
        }
	}

	public MediaType getMediaType(long mediaTypeId) {
		Connection conn = db.connect();
        PreparedStatement getMediaType = null;
        ResultSet results = null;
        try {
        	getMediaType = conn.prepareStatement("SELECT Name FROM MediaType WHERE MediaTypeId = ?");
        	getMediaType.setLong(1, mediaTypeId);
        	results = getMediaType.executeQuery();	
        	if(results.next()) {
        		return new MediaType(mediaTypeId, results.getString("Name"));
        	} else {
        		return null;
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        } finally {
        	db.close(results, getMediaType, conn);
        }
	}
}

