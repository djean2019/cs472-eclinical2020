package repository.patientdoctor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.doctorpatient.DoctorSpecialization;
import model.doctorpatient.Person;
import model.doctorpatient.Specialization;

public class DoctorSpecializationRepository {
	private static DoctorSpecializationRepository doctorSpecializationRepository;
	private Database database = Database.getInstance();
	private DoctorSpecializationRepository() {}
	public static DoctorSpecializationRepository getInstance() {
		if(doctorSpecializationRepository == null)
			doctorSpecializationRepository = new DoctorSpecializationRepository();
		return doctorSpecializationRepository;
	}
	public boolean saveDoctorSpecialization(DoctorSpecialization doctorSpecialization) {
		boolean isSuccess = false;
		try {
			if(doctorSpecialization != null) {
				database.executeStatement("INSERT into doctor_specialization(specialization_id, doctor_id) VALUES(?, ?)", 
							Arrays.asList(doctorSpecialization.getSpecialiation().getSpecializationId(),
										  doctorSpecialization.getDoctor().getPersonId()));
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<DoctorSpecialization> loadDoctorSpecializations() {
		List<DoctorSpecialization> doctorSpecializations = new ArrayList<>();
		DoctorRepository doctorRepo = DoctorRepository.getInstance();
		SpecializationRepository specializationRepo = SpecializationRepository.getInstance();
		try {
			ResultSet result = database.getResult("SELECT * FROM doctor_specialization", null);
			while(result.next()) {
				Person doctor = doctorRepo.loadDoctorById(result.getInt("doctor_id"));
				Specialization specialization = specializationRepo.loadSpecializationById(result.getInt("specialization_id"));
				doctorSpecializations.add(new DoctorSpecialization(result.getInt(1), specialization, doctor));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return doctorSpecializations;
	}
	public List<Specialization> loadDoctorSpecializations(int doctorId) {
		List<Specialization> doctorSpecializations = new ArrayList<>();
		SpecializationRepository specializationRepo = SpecializationRepository.getInstance();
		try {
			ResultSet result = database.getResult("SELECT * FROM doctor_specialization WHERE doctor_id = ?", Arrays.asList(doctorId));
			while(result.next()) 
				doctorSpecializations.add(specializationRepo.loadSpecializationById(result.getInt("specialization_id")));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return doctorSpecializations;
	}
	public boolean deleteDoctorSpecializationById(int doctorSpecializationId) {
		boolean isSuccess = false;
		try {
			database.executeStatement("DELETE FROM doctor_specialization where doctor_specialization_id = ?", Arrays.asList(doctorSpecializationId));
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
