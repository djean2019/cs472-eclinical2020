package repository.checkinout;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.appointment.Appointment;
import model.checkinout.CheckInOut;
import model.doctorpatient.Person;
import repository.appointment.AppointmentRepository;
import repository.patientdoctor.DoctorRepository;

public class CheckInOutRepository {
	private static CheckInOutRepository checkInOutRepository;
	private Database database = Database.getInstance();
	private CheckInOutRepository() {}
	public static CheckInOutRepository getInstance() {
		if(checkInOutRepository == null)
			checkInOutRepository = new CheckInOutRepository();
		return checkInOutRepository;
	}
	public boolean saveCheckInOut(CheckInOut checkInOut) {
		boolean isSuccess = false;
		try {
			if(checkInOut != null) {
				database.executeStatement("INSERT into checkinout(appointmentid, doctorid, checkindatetime, checkoutdatetime, charge, description) VALUES(?, ?, ?, ?, ?, ?)", 
							Arrays.asList(checkInOut.getAppointment().getAppointmentId(),
										  checkInOut.getDoctor().getPersonId(),
										  checkInOut.getCheckInDateTime(),
										  checkInOut.getCheckOutDateTime(),
										  checkInOut.getCharge(),
										  checkInOut.getDescription()));
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<CheckInOut> loadCheckInOuts() {
		List<CheckInOut> checkinouts = new ArrayList<>();
		AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
		DoctorRepository doctorRepo = DoctorRepository.getInstance();
		try {
			ResultSet result = database.getResult("SELECT * FROM checkinout", null);
			while(result.next()) {
				Appointment appointment = appointmentRepo.loadAppointmentById(result.getInt("appointmentid"));
				Person doctor = doctorRepo.loadDoctorById(result.getInt("doctorid"));
				checkinouts.add(new CheckInOut(result.getInt(1), 
											   appointment, 
											   doctor, 
											   result.getTimestamp("checkindatetime").toLocalDateTime(), 
											   result.getTimestamp("checkoutdatetime").toLocalDateTime(), 
											   result.getDouble("charge"), 
											   result.getString("description")));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return checkinouts;
	}
	public CheckInOut loadCheckInOutById(int checkInOutId) {
		CheckInOut checkinout = new CheckInOut();
		AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
		DoctorRepository doctorRepo = DoctorRepository.getInstance();
		try {
			ResultSet result = database.getResult("SELECT * FROM checkinout", null);
			if(result.next()) {
				Appointment appointment = appointmentRepo.loadAppointmentById(result.getInt("appointmentid"));
				Person doctor = doctorRepo.loadDoctorById(result.getInt("doctorid"));
				checkinout = new CheckInOut(result.getInt(1), 
										    appointment, 
											doctor, 
											result.getTimestamp("checkindatetime").toLocalDateTime(), 
											result.getTimestamp("checkoutdatetime").toLocalDateTime(), 
											result.getDouble("charge"), 
											result.getString("description"));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return checkinout;
	}
	public boolean deleteCheckInOutById(int checkInOutId) {
		boolean isSuccess = false;
		try {
			database.executeStatement("DELETE FROM checkinout where checkinoutid = ?", Arrays.asList(checkInOutId));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
}
