package repository.patientdoctor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.doctorpatient.Specialization;

public class SpecializationRepository {
	private static SpecializationRepository specializationRepository;
	private Database database = Database.getInstance();
	private SpecializationRepository() {}
	public static SpecializationRepository getInstance() {
		if(specializationRepository == null)
			specializationRepository = new SpecializationRepository();
		return specializationRepository;
	}
	public boolean saveSpecialization(Specialization specialization) {
		boolean isSuccess = false;
		try {
			if(specialization != null) {
				database.executeStatement("INSERT into specialization(specializationname) VALUES(?)", Arrays.asList(specialization.getSpecializationName()));
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<Specialization> loadSpecializations() {
		List<Specialization> specializations = new ArrayList<>();
		try {
			ResultSet result = database.getResult("SELECT * FROM specialization", null);
			while(result.next()) 
				specializations.add(new Specialization(result.getInt(1), result.getString(2)));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return specializations;
	}
	public Specialization loadSpecializationById(int specializationId) {
		Specialization specialization = new Specialization();
		try {
			ResultSet result = database.getResult("SELECT * FROM specialization where specializationid = ?", Arrays.asList(specializationId));
			if(result.next()) 
				specialization = new Specialization(result.getInt(1), result.getString(2));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return specialization;
	}
	public boolean deleteSpecializationById(int specializationId) {
		boolean isSuccess = false;
		try {
			database.executeStatement("DELETE FROM specialization where specializationid = ?", Arrays.asList(specializationId));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public boolean updateSpecializationById(Specialization specialization) {
		boolean isSuccess = false;
		try {
			database.executeStatement("UPDATE specialization SET specializationname = ? WHERE specializationid = ?", Arrays.asList(specialization.getSpecializationName(), specialization.getSpecializationId()));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
}
