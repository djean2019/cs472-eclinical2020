package model.doctorpatient;

public class DoctorSpecialization {
	private int doctorSpecializationId;
	private Specialization specialiation;
	private Person doctor;
	
	public DoctorSpecialization(Specialization specialiation, Person doctor) {
		super();
		this.specialiation = specialiation;
		this.doctor = doctor;
	}
	public DoctorSpecialization(int doctorSpecializationId, Specialization specialiation, Person doctor) {
		super();
		this.doctorSpecializationId = doctorSpecializationId;
		this.specialiation = specialiation;
		this.doctor = doctor;
	}
	public int getDoctorSpecializationId() {
		return doctorSpecializationId;
	}
	public void setDoctorSpecializationId(int doctorSpecializationId) {
		this.doctorSpecializationId = doctorSpecializationId;
	}
	public Specialization getSpecialiation() {
		return specialiation;
	}
	public void setSpecialiation(Specialization specialiation) {
		this.specialiation = specialiation;
	}
	public Person getDoctor() {
		return doctor;
	}
	public void setDoctor(Person doctor) {
		this.doctor = doctor;
	}
}
