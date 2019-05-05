package model;

public class Song {

    private long id;
    private String name;
    private String genre;
    private String mediaType;
    private String songLength;

    public Song(long id, String name, String genre, String mediaType, long songLength) {
        this.setId(id);
        this.setName(name);
        this.setGenre(genre);
        this.setMediaType(mediaType);
        this.setSongLength(minutesAndSeconds(songLength));
    }
    
    private String minutesAndSeconds(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String rs = minutes + ":" + seconds;
        
        // if 0-9 then add 0 in front of number
        if(String.valueOf(seconds).length() == 1) {
        	rs = minutes + ":0" + seconds; 
        } 
   
        return rs;
    	
    }
    
    public static long secondsInMilliseconds(long seconds) {
    	return seconds * 1000;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getSongLength() {
		return songLength;
	}

	public void setSongLength(String songLength) {
		this.songLength = songLength;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    
    
 
}

