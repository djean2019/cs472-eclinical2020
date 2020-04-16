package repository.patientdoctor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.doctorpatient.Person;

public class PatientRepository {
	private static PatientRepository patientRepository;
	private Database database = Database.getInstance();
	private PatientRepository() {}
	public static PatientRepository getInstance() {
		if(patientRepository == null)
			patientRepository = new PatientRepository();
		return patientRepository;
	}
	public boolean savePatient(Person patient) {
		boolean isSuccess = false;
		try {
			if(patient != null) {
				//load auto patientNumber here
				ResultSet result = database.getResult("SELECT IFNULL(MAX(RIGHT(patient_number, LENGTH(patient_number) - 3)),0) + 1 As patient_number FROM patient", null);
				if(result.next())
					patient.setPersonNumber("PT#" + result.getInt("patient_number"));
				List<Object> parameters = Arrays.asList(patient.getPersonNumber(), patient.getFirstName(), 
						patient.getMiddleName(), patient.getLastName(), patient.getContactPhone(), patient.getAddress());
				database.executeStatement("INSERT INTO patient(patient_number, firstname, middlename, lastname, contactphone, address) "
						+ "VALUES(?, ?, ?, ?, ?, ?)", parameters);
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<Person> loadPatients() {
		List<Person> patients = new ArrayList<>();
		try {
			ResultSet result = database.getResult("SELECT * FROM patient", null);
			while(result.next()) {
				patients.add(new Person(result.getInt(1), 
										result.getString(2), 
										result.getString(3), 
										result.getString(4), 
										result.getString(5), 
										result.getString(6), 
										result.getString(7)));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return patients;
	}
	public Person loadPatientById(int patientId) {
		Person patient = new Person();
		try {
			ResultSet result = database.getResult("SELECT * FROM patient WHERE patientid = ?", Arrays.asList(patientId));
			if(result.next()) {
				patient = new Person(result.getInt(1), 
									result.getString(2), 
									result.getString(3), 
									result.getString(4), 
									result.getString(5), 
									result.getString(6), 
									result.getString(7));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return patient;
	}
	public boolean deletePatientById(int patientId) {
		boolean isSuccess = false;
		try {
			database.executeStatement("DELETE FROM patient where patientid = ?", Arrays.asList(patientId));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public boolean updatePatientById(Person patient) {
		boolean isSuccess = false;
		try {
			List<Object> parameters = Arrays.asList(patient.getFirstName(), 
					patient.getMiddleName(), patient.getLastName(), patient.getContactPhone(), patient.getAddress(), patient.getPersonId());
			database.executeStatement("UPDATE patient SET firstname = ?, middlename = ?, lastname = ?, contactphone = ?, address = ? WHERE patientid = ?", parameters);
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
}
