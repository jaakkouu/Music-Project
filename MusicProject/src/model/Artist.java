package model;

public class Artist {

    private long id;
    private String name;
    private long albumCount;

    public Artist(long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public long getId() {
    	return this.id;
    }
    
    public void setId(long id) {
    	this.id = id;
    }

	public long getAlbumCount() {
		return this.albumCount;
	}

	public void setAlbumCount(long albumCount) {
		this.albumCount = albumCount;
	}
    
}

