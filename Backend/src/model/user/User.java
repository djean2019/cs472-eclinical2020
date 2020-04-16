package model.user;

import model.doctorpatient.Person;

public class User {
	private int userId;
	private String username;
	private String password;
	private boolean isLock;
	private UserType userType;
	private Person person;
	public User() {}
	
	public User(int userId) {
		super();
		this.userId = userId;
	}

	public User(String username, String password, UserType userType, Person person) {
		super();
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.person = person;
	}
	public User(String username, String password, boolean isLock, UserType userType, Person person) {
		super();
		this.username = username;
		this.password = password;
		this.isLock = isLock;
		this.userType = userType;
		this.person = person;
	}
	public User(int userId, String username, String password, boolean isLock, UserType userType, Person person) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.isLock = isLock;
		this.userType = userType;
		this.person = person;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLock() {
		return isLock;
	}
	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
}
