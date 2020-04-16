package repository.user;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.user.UserType;

public class UserTypeRepository {
	private static UserTypeRepository userTypeRepository;
	private Database database = Database.getInstance();
	private UserTypeRepository() {}
	public static UserTypeRepository getInstance() {
		if(userTypeRepository == null)
			userTypeRepository = new UserTypeRepository();
		return userTypeRepository;
	}
	public boolean saveUserType(UserType userType) throws Exception {
		boolean isSuccess = false;
		if(userType != null) {
			database.executeStatement("INSERT into usertype(usertypename) VALUES(?)", Arrays.asList(userType.getUserTypeName()));
			isSuccess = true;
		}
		return isSuccess;
	}
	public List<UserType> loadUserTypes() throws Exception {
		List<UserType> userTypes = new ArrayList<>();
		ResultSet result = database.getResult("SELECT * FROM usertype", null);
		while(result.next()) {
			userTypes.add(new UserType(result.getInt(1), result.getString(2)));
		}
		return userTypes;
	}
	public UserType loadUserTypeById(int userTypeId) throws Exception {
		UserType userType = new UserType();
		ResultSet result = database.getResult("SELECT * FROM usertype WHERE usertypeid = ?", Arrays.asList(userTypeId));
		if(result.next()) 
			userType = new UserType(result.getInt(1), result.getString(2));
		return userType;
	}
	public boolean deleteUserTypeById(int userTypeId) throws Exception {
		boolean isSuccess = false;
		database.executeStatement("DELETE FROM usertype where usertypeid = ?", Arrays.asList(userTypeId));
		isSuccess = true;
		return isSuccess;
	}
	public boolean updateUserTypeById(UserType userType) throws Exception {
		boolean isSuccess = false;
		database.executeStatement("UPDATE usertype SET usertypename = ? WHERE usertypeid = ?", Arrays.asList(userType.getUserTypeName(), userType.getUserTypeId()));
		isSuccess = true;
		return isSuccess;
	}
}
