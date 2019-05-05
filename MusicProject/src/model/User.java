package model;

public class User {
	
	private long id;
	private String username;

	public User(long id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
}
