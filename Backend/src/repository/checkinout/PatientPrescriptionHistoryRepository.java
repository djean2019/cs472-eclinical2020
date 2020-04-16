package repository.checkinout;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.checkinout.CheckInOut;
import model.checkinout.PatientPrescriptionHistory;
import model.medicine.Medicine;
import repository.medicine.MedicineRepository;

public class PatientPrescriptionHistoryRepository {
	private static PatientPrescriptionHistoryRepository patientPrescriptionHistoryRepository;
	private Database database = Database.getInstance();
	private PatientPrescriptionHistoryRepository() {}
	public static PatientPrescriptionHistoryRepository getInstance() {
		if(patientPrescriptionHistoryRepository == null)
			patientPrescriptionHistoryRepository = new PatientPrescriptionHistoryRepository();
		return patientPrescriptionHistoryRepository;
	}
	public boolean savePrescriptionHistory(PatientPrescriptionHistory patientPrescriptionHistory) {
		boolean isSuccess = false;
		try {
			if(patientPrescriptionHistory != null) {
				database.executeStatement("INSERT into patient_prescription_history(checkinoutid, medicineid) VALUES(?, ?)", 
							Arrays.asList(patientPrescriptionHistory.getCheckInOut().getCheckInOutId(),
										  patientPrescriptionHistory.getMedicine().getMedicineId()));
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<PatientPrescriptionHistory> loadPrescriptionHistorys() {
		List<PatientPrescriptionHistory> patientPrescriptionHistory = new ArrayList<>();
		CheckInOutRepository checkInOutRepo = CheckInOutRepository.getInstance();
		MedicineRepository medicineRepo = MedicineRepository.getInstance();
		try {
			ResultSet result = database.getResult("SELECT * FROM patient_prescription_history", null);
			while(result.next()) {
				CheckInOut checkInOut = checkInOutRepo.loadCheckInOutById(result.getInt("checkinoutid"));
				Medicine medicine = medicineRepo.loadMedicineById(result.getInt("medicineid"));
				patientPrescriptionHistory.add(new PatientPrescriptionHistory(result.getInt(1), checkInOut, medicine));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return patientPrescriptionHistory;
	}
	public boolean deletePrescriptionHistoryById(int prescriptionHistoryId) {
		boolean isSuccess = false;
		try {
			 database.executeStatement("DELETE FROM patient_prescription_history where patient_prescription_id = ?", Arrays.asList(prescriptionHistoryId));
			 isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
}
