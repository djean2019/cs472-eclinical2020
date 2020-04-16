package repository.checkinout;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.checkinout.CheckInOut;
import model.checkinout.PatientDeseaseHistory;
import model.desease.Desease;
import repository.desease.DeseaseRepository;

public class PatientDeseaseHistoryRepository {
	private static PatientDeseaseHistoryRepository patientDeseaseHistoryRepository;
	private Database database = Database.getInstance();
	private PatientDeseaseHistoryRepository() {}
	public static PatientDeseaseHistoryRepository getInstance() {
		if(patientDeseaseHistoryRepository == null)
			patientDeseaseHistoryRepository = new PatientDeseaseHistoryRepository();
		return patientDeseaseHistoryRepository;
	}
	public boolean saveDeseaseHistory(PatientDeseaseHistory patientDeseaseHistory) {
		boolean isSuccess = false;
		try {
			if(patientDeseaseHistoryRepository != null) {
				database.executeStatement("INSERT into patient_desease_history(checkinoutid, deseaseid) VALUES(?, ?)", 
							Arrays.asList(patientDeseaseHistory.getCheckInOut().getCheckInOutId(),
										  patientDeseaseHistory.getDesease().getDeseaseId()));
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<PatientDeseaseHistory> loadDeseaseHistorys() {
		List<PatientDeseaseHistory> patientDeseaseHistorys = new ArrayList<>();
		CheckInOutRepository checkInOutRepo = CheckInOutRepository.getInstance();
		DeseaseRepository deseaseRepo = DeseaseRepository.getInstance();
		try {
			ResultSet result = database.getResult("SELECT * FROM patient_desease_history", null);
			while(result.next()) {
				CheckInOut checkInOut = checkInOutRepo.loadCheckInOutById(result.getInt("checkinoutid"));
				Desease desease = deseaseRepo.loadDeseaseById(result.getInt("deseaseid"));
				patientDeseaseHistorys.add(new PatientDeseaseHistory(result.getInt(1), checkInOut, desease));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return patientDeseaseHistorys;
	}
	public boolean deleteDeseaseHistoryById(int deseaseHistoryId) {
		boolean isSuccess = false;
		try {
			database.executeStatement("DELETE FROM patient_desease_history where patient_desease_id = ?", Arrays.asList(deseaseHistoryId));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
}
