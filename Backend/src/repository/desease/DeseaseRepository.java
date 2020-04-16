package repository.desease;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.desease.Desease;
import model.desease.DeseaseCategory;

public class DeseaseRepository {
	private static DeseaseRepository deseaseRepository;
	private Database database = Database.getInstance();
	private DeseaseRepository() {}
	public static DeseaseRepository getInstance() {
		if(deseaseRepository == null)
			deseaseRepository = new DeseaseRepository();
		return deseaseRepository;
	}
	public boolean saveDesease(Desease desease) {
		boolean isSuccess = false;
		try {
			if(desease != null) {
				database.executeStatement("INSERT into desease(deseasename, desease_category_id) VALUES(?, ?)", 
													  Arrays.asList(desease.getDeseaseName(), desease.getdeseaseCategory().getDeseaseTypeId()));
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<Desease> loadDeseases() {
		List<Desease> deseases = new ArrayList<>();
		DeseaseCategoryRepository deseaseCategoryRepo = DeseaseCategoryRepository.getInstance();
		try {
			ResultSet deseaseResult = database.getResult("SELECT * FROM desease", null);
			while(deseaseResult.next()) {
				DeseaseCategory deseaseCategory = deseaseCategoryRepo.loadDeseaseCategoryById(deseaseResult.getInt("desease_category_id"));
				deseases.add(new Desease(deseaseResult.getInt(1), deseaseResult.getString(2), deseaseCategory));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return deseases;
	
	}
	public Desease loadDeseaseById(int deseaseId) {
		Desease desease = new Desease();
		DeseaseCategoryRepository deseaseCategoryRepo = DeseaseCategoryRepository.getInstance();
		try {
			ResultSet deseaseResult = database.getResult("SELECT * FROM desease WHERE deseaseid = ?", Arrays.asList(deseaseId));
			if(deseaseResult.next()) {
				DeseaseCategory deseaseCategory = deseaseCategoryRepo.loadDeseaseCategoryById(deseaseResult.getInt("desease_category_id"));
				desease = new Desease(deseaseResult.getInt(1), deseaseResult.getString(2), deseaseCategory);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return desease;
	}
	public boolean deleteDeseaseById(int deseaseId) {
		boolean isSuccess = false;
		try {
			database.executeStatement("DELETE FROM desease where deseaseid = ?", Arrays.asList(deseaseId));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public boolean updateDeseaseById(Desease desease) {
		boolean isSuccess = false;
		try {
			database.executeStatement("UPDATE desease SET deseasename = ?, desease_category_id = ? WHERE deseaseid = ?", 
												   Arrays.asList(desease.getDeseaseName(), 
																 desease.getdeseaseCategory().getDeseaseTypeId(),
																 desease.getDeseaseId()));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
}
