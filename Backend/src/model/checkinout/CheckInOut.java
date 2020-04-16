package model.checkinout;

import java.time.LocalDateTime;

import model.appointment.Appointment;
import model.doctorpatient.Person;

public class CheckInOut {
	private int checkInOutId;
	private Appointment appointment;
	private Person doctor;
	private LocalDateTime checkInDateTime;
	private LocalDateTime checkOutDateTime;
	private double charge;
	private String description;
	public CheckInOut() {}
	
	public CheckInOut(Appointment appointment, Person doctor, LocalDateTime checkInDateTime,
			LocalDateTime checkOutDateTime, double charge, String description) {
		super();
		this.appointment = appointment;
		this.doctor = doctor;
		this.checkInDateTime = checkInDateTime;
		this.checkOutDateTime = checkOutDateTime;
		this.charge = charge;
		this.description = description;
	}

	public CheckInOut(int checkInOutId, Appointment appointment, Person doctor, LocalDateTime checkInDateTime,
			LocalDateTime checkOutDateTime, double charge, String description) {
		super();
		this.checkInOutId = checkInOutId;
		this.appointment = appointment;
		this.doctor = doctor;
		this.checkInDateTime = checkInDateTime;
		this.checkOutDateTime = checkOutDateTime;
		this.charge = charge;
		this.description = description;
	}
	public int getCheckInOutId() {
		return checkInOutId;
	}
	public void setCheckInOutId(int checkInOutId) {
		this.checkInOutId = checkInOutId;
	}
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	public Person getDoctor() {
		return doctor;
	}
	public void setDoctor(Person doctor) {
		this.doctor = doctor;
	}
	public LocalDateTime getCheckInDateTime() {
		return checkInDateTime;
	}
	public void setCheckInDateTime(LocalDateTime checkInDateTime) {
		this.checkInDateTime = checkInDateTime;
	}
	public LocalDateTime getCheckOutDateTime() {
		return checkOutDateTime;
	}
	public void setCheckOutDateTime(LocalDateTime checkOutDateTime) {
		this.checkOutDateTime = checkOutDateTime;
	}
	public double getCharge() {
		return charge;
	}
	public void setCharge(double charge) {
		this.charge = charge;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
