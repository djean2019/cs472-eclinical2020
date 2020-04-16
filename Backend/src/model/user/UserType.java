package model.user;

public class UserType {
	private int userTypeId;
	private String userTypeName;
	public UserType() {}
	
	public UserType(String userTypeName) {
		super();
		this.userTypeName = userTypeName;
	}

	public UserType(int userTypeId) {
		super();
		this.userTypeId = userTypeId;
	}

	public UserType(int userTypeId, String userTypeName) {
		super();
		this.userTypeId = userTypeId;
		this.userTypeName = userTypeName;
	}
	public int getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
}
