package model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import model.doctorpatient.Specialization;
import model.user.User;

public class Appointment {
	private int appointmentId;
	private String appointmentNumber;
	private LocalDate appointmentDate;
	private LocalTime appointmentTime;
	private Specialization specialization;
	private User appointedBy;
	public Appointment() {}
	
	public Appointment(int appointmentId) {
		super();
		this.appointmentId = appointmentId;
	}

	public Appointment(LocalDate appointmentDate, LocalTime appointmentTime, Specialization specialization,
			User appointedBy) {
		super();
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.specialization = specialization;
		this.appointedBy = appointedBy;
	}

	public Appointment(int appointmentId, String appointmentNumber, LocalDate appointmentDate,
			LocalTime appointmentTime, Specialization specialization, User appointedBy) {
		super();
		this.appointmentId = appointmentId;
		this.appointmentNumber = appointmentNumber;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.specialization = specialization;
		this.appointedBy = appointedBy;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getAppointmentNumber() {
		return appointmentNumber;
	}
	public void setAppointmentNumber(String appointmentNumber) {
		this.appointmentNumber = appointmentNumber;
	}
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public LocalTime getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(LocalTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public Specialization getSpecialization() {
		return specialization;
	}
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}
	public User getAppointedBy() {
		return appointedBy;
	}
	public void setAppointedBy(User appointedBy) {
		this.appointedBy = appointedBy;
	}
}
