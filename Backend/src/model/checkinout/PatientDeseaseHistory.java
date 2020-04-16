package model.checkinout;

import model.desease.Desease;

public class PatientDeseaseHistory {
	private int patientDeseaseHistoryId;
	private CheckInOut checkInOut;
	private Desease desease;
	public PatientDeseaseHistory(int patientDeseaseHistoryId, CheckInOut checkInOut, Desease desease) {
		super();
		this.patientDeseaseHistoryId = patientDeseaseHistoryId;
		this.checkInOut = checkInOut;
		this.desease = desease;
	}
	public int getPatientDeseaseHistoryId() {
		return patientDeseaseHistoryId;
	}
	public void setPatientDeseaseHistoryId(int patientDeseaseHistoryId) {
		this.patientDeseaseHistoryId = patientDeseaseHistoryId;
	}
	public CheckInOut getCheckInOut() {
		return checkInOut;
	}
	public void setCheckInOut(CheckInOut checkInOut) {
		this.checkInOut = checkInOut;
	}
	public Desease getDesease() {
		return desease;
	}
	public void setDesease(Desease desease) {
		this.desease = desease;
	}
}
