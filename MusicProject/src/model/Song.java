package model;

public class Song {

    private long id;
    private String name,
	    songLength,
	    unitPrice;
    
    private MediaType mediaType;
    private Genre genre;
    
    public Song(long id, String name, Genre genre, MediaType mediaType, long songLength, String UnitPrice) {
        this.setId(id);
        this.setName(name);
        this.setGenre(genre);
        this.setMediaType(mediaType);
        this.setSongLength(minutesAndSeconds(songLength));
        this.setUnitPrice(UnitPrice);
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
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Genre getGenre() {
		return genre;
	}
	
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getSongLength() {
		return this.songLength;
	}
	
	public String getSongLengthInSeconds() {
		String[] minutesAndSeconds = this.songLength.split(":");
		long minutes = Long.parseLong(minutesAndSeconds[0]) * 60;
		long seconds = Long.parseLong(minutesAndSeconds[1]);
		return Long.toString(minutes + seconds);
	}

	public void setSongLength(String songLength) {
		this.songLength = songLength;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public String getUnitPrice() {
		return this.unitPrice;
	}
    
    
 
}

