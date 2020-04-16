package repository.appointment;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.appointment.Appointment;
import model.doctorpatient.Specialization;
import model.user.User;
import repository.patientdoctor.SpecializationRepository;
import repository.user.UserRepository;


public class AppointmentRepository {
	private static AppointmentRepository appointmentRepository;
	private Database database = Database.getInstance();
	private AppointmentRepository() {}
	public static AppointmentRepository getInstance() {
		if(appointmentRepository == null)
			appointmentRepository = new AppointmentRepository();
		return appointmentRepository;
	}
	public boolean saveAppointmentRepository(Appointment appointment) {
		boolean isSuccess = false;
		try {
			if(appointment != null) {
				//load auto appointmentNumber here
				ResultSet result = database.getResult("SELECT IFNULL(MAX(RIGHT(appointment_number, LENGTH(appointment_number) - 3)),0) + 1 As appointment_number FROM appointment", null);
				if(result.next())
					appointment.setAppointmentNumber("AP#" + result.getInt("appointment_number"));
				database.executeStatement("INSERT into appointment(appointment_number, appointmentdate, appointmentime, specializationid, appointedby) VALUES(?, ?, ?, ?, ?)", 
							Arrays.asList(appointment.getAppointmentNumber(),
										  appointment.getAppointmentDate(),
										  appointment.getAppointmentTime(),
										  appointment.getSpecialization().getSpecializationId(),
										  appointment.getAppointedBy().getUserId()));
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<Appointment> loadAppointments() {
		List<Appointment> appointments = new ArrayList<>();
		UserRepository userRepo = UserRepository.getInstance();
		SpecializationRepository specializationRepo = SpecializationRepository.getInstance();
		try {
			ResultSet result = database.getResult("SELECT * FROM appointment", null);
			while(result.next()) {
				User user = userRepo.loadUserById(result.getInt("appointedby"));
				
				Specialization specialization = specializationRepo.loadSpecializationById(result.getInt("specializationid"));
				appointments.add(new Appointment(result.getInt(1), result.getString(2), 
												 result.getDate(3).toLocalDate(), 
												 result.getTime(4).toLocalTime(), 
												 specialization, 
												 user));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return appointments;
	}
	public List<Appointment> loadTodayAppointments() {
		List<Appointment> appointments = new ArrayList<>();
		UserRepository userRepo = UserRepository.getInstance();
		SpecializationRepository specializationRepo = SpecializationRepository.getInstance();
		try {
			ResultSet result = database.getResult("SELECT * FROM appointment WHERE appointmentdate = CAST(now() AS DATE)", null);
			while(result.next()) {
				User user = userRepo.loadUserById(result.getInt("appointedby"));
				
				Specialization specialization = specializationRepo.loadSpecializationById(result.getInt("specializationid"));
				appointments.add(new Appointment(result.getInt(1), result.getString(2), 
												 result.getDate(3).toLocalDate(), 
												 result.getTime(4).toLocalTime(), 
												 specialization, 
												 user));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return appointments;
	}
	public Appointment loadAppointmentById(int appointmentId) {
		Appointment appointment = new Appointment();
		UserRepository userRepo = UserRepository.getInstance();
		SpecializationRepository specializationRepo = SpecializationRepository.getInstance();
		try {
			ResultSet result = database.getResult("SELECT * FROM appointment where appointmentid = ?", Arrays.asList(appointmentId));
			while(result.next()) {
				User user = userRepo.loadUserById(result.getInt("appointedby"));
				
				Specialization specialization = specializationRepo.loadSpecializationById(result.getInt("specializationid"));
				appointment = new Appointment(result.getInt(1), result.getString(2), 
											  result.getDate(3).toLocalDate(), 
											  result.getTime(4).toLocalTime(), 
											  specialization, 
											  user);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return appointment;
	}
	public List<LocalTime> checkAvailability(LocalDate appointmentDate) {
		List<LocalTime> localTimes = new ArrayList<LocalTime>();
		localTimes.add(LocalTime.of(10, 00));
		localTimes.add(LocalTime.of(11, 00));
		localTimes.add(LocalTime.of(12, 00));
		
		localTimes.add(LocalTime.of(14, 00));
		localTimes.add(LocalTime.of(15, 00));
		localTimes.add(LocalTime.of(16, 00));
		localTimes.add(LocalTime.of(17, 00));
		
		ResultSet result = database.getResult("SELECT appointmentime FROM appointment where appointmentdate = ?", Arrays.asList(appointmentDate));
		if(result != null) {
			try {
				while(result.next()) {
					LocalTime appointmentTime = result.getTime(1).toLocalTime();
					for(int i=0; i<localTimes.size(); i++)
						if(appointmentTime.equals(localTimes.get(i))) {
							localTimes.remove(i);
							break;
						}
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return localTimes;
	}
	
	public boolean deleteAppointmentById(int appointmentId) {
		boolean isSuccess = false;
		try {
			database.executeStatement("DELETE FROM appointment where appointmentid = ?", Arrays.asList(appointmentId));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public boolean updateAppointmentById(Appointment appointment) {
		boolean isSuccess = false;
		try {
			database.executeStatement("UPDATE appointment SET appointmentdate = ?, appointmenttime = ?, specializationid = ?, appointedby = ? WHERE appointmentid = ?", 
					Arrays.asList(appointment.getAppointmentDate(), appointment.getAppointmentTime(), appointment.getSpecialization().getSpecializationId(), appointment.getAppointedBy().getUserId()));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
}
