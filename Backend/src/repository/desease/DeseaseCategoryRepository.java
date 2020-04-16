package repository.desease;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.desease.DeseaseCategory;

public class DeseaseCategoryRepository {
	private static DeseaseCategoryRepository deseaseCategoryRepository;
	private Database database = Database.getInstance();
	private DeseaseCategoryRepository() {}
	public static DeseaseCategoryRepository getInstance() {
		if(deseaseCategoryRepository == null)
			deseaseCategoryRepository = new DeseaseCategoryRepository();
		return deseaseCategoryRepository;
	}
	public boolean saveDeseaseCategory(DeseaseCategory deseaseCategory) {
		boolean isSuccess = false;
		try {
			if(deseaseCategory != null) {
				database.executeStatement("INSERT into desease_category(deseasecategoryname) VALUES(?)", Arrays.asList(deseaseCategory.getDeseaseTypeName()));
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<DeseaseCategory> loadDeseaseCategory() {
		List<DeseaseCategory> deseaseCategories = new ArrayList<>();
		try {
			ResultSet result = database.getResult("SELECT * FROM desease_category", null);
			while(result.next()) 
				deseaseCategories.add(new DeseaseCategory(result.getInt(1), result.getString(2)));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return deseaseCategories;
	}
	public DeseaseCategory loadDeseaseCategoryById(int deseaseCategoryId) {
		DeseaseCategory deseaseCategory = new DeseaseCategory();
		try {
			ResultSet result = database.getResult("SELECT * FROM desease_category WHERE deseasecategoryid = ?", Arrays.asList(deseaseCategoryId));
			if(result.next()) 
				deseaseCategory = new DeseaseCategory(result.getInt(1), result.getString(2));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return deseaseCategory;
	}
	public boolean deleteDeseaseCategoryById(int deseaseCategoryId) {
		boolean isSuccess = false;
		try {
			database.executeStatement("DELETE FROM desease_category where deseasecategoryid = ?", Arrays.asList(deseaseCategoryId));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public boolean updateDeseaseCategoryById(DeseaseCategory deseaseCategory) {
		boolean isSuccess = false;
		try {
			database.executeStatement("UPDATE desease_category SET deseasecategoryname = ? WHERE deseasecategoryid = ?", Arrays.asList(deseaseCategory.getDeseaseTypeName(), deseaseCategory.getDeseaseTypeId()));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
}
