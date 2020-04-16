package model.checkinout;

import model.medicine.Medicine;

public class PatientPrescriptionHistory {
	private int patientPrescriptionHistoryId;
	private CheckInOut checkInOut;
	private Medicine medicine;
	public PatientPrescriptionHistory(int patientPrescriptionHistoryId, CheckInOut checkInOut, Medicine medicine) {
		super();
		this.patientPrescriptionHistoryId = patientPrescriptionHistoryId;
		this.checkInOut = checkInOut;
		this.medicine = medicine;
	}
	public int getPatientPrescriptionHistoryId() {
		return patientPrescriptionHistoryId;
	}
	public void setPatientPrescriptionHistoryId(int patientPrescriptionHistoryId) {
		this.patientPrescriptionHistoryId = patientPrescriptionHistoryId;
	}
	public CheckInOut getCheckInOut() {
		return checkInOut;
	}
	public void setAppointment(CheckInOut checkInOut) {
		this.checkInOut = checkInOut;
	}
	public Medicine getMedicine() {
		return medicine;
	}
	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
}
