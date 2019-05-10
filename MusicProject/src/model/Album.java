package model;

public class Album {
	
	private long id,
			artistId,
			songCount;
	private String name;
	
	public Album(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}

	public long getSongCount() {
		return songCount;
	}

	public void setSongCount(long songCount) {
		this.songCount = songCount;
	}

	public void setArtistId(long artistId) {
		this.artistId = artistId;
	}
	
	public long getArtistId() {
		return this.artistId;
	}

}
