package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Database {
	private Connection connection;
	private PreparedStatement statement = null;
	private static Database database;
	// JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/eclinical?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //  Database credentials
    static final String USER = "xsron";
    static final String PASS = "";
    
	private Database() {}
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		if(connection == null) {
			//STEP 2: Register JDBC driver
		    Class.forName(JDBC_DRIVER);
		    //STEP 3: Open a connection
		    connection = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		}
		return connection;
	}
	public static Database getInstance() {
		if(database == null)
			database = new Database();
		return database;
	}
	public boolean executeStatement(String sql, List<Object> objects) {
		boolean isSuccess = false;
		try {
			statement = getConnection().prepareStatement(sql);
			if(objects != null) {
				for(int i=0; i<objects.size(); i++)
					statement.setObject(i + 1, objects.get(i));
			}
			isSuccess = statement.execute();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public int executeStatementWithLastInsertedId(String sql, List<Object> objects) {
		int lastInsertedId = 0;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if(objects != null) {
				for(int i=0; i<objects.size(); i++)
					statement.setObject(i + 1, objects.get(i));
			}
			statement.execute();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) 
				lastInsertedId = rs.getInt(1);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return lastInsertedId;
	}
	public ResultSet getResult(String sql, List<Object> objects) {
		try {
			statement = getConnection().prepareStatement(sql);
			if(objects != null) {
				for(int i=0; i<objects.size(); i++)
					statement.setObject(i + 1, objects.get(i));
			}
			return statement.executeQuery();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
