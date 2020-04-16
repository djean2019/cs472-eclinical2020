package model.doctorpatient;

public class Specialization {
	private int specializationId;
	private String specializationName;
	public Specialization() {}
	
	public Specialization(int specializationId) {
		super();
		this.specializationId = specializationId;
	}

	public Specialization(String specializationName) {
		super();
		this.specializationName = specializationName;
	}

	public Specialization(int specializationId, String specializationName) {
		super();
		this.specializationId = specializationId;
		this.specializationName = specializationName;
	}
	public int getSpecializationId() {
		return specializationId;
	}
	public void setSpecializationId(int specializationId) {
		this.specializationId = specializationId;
	}
	public String getSpecializationName() {
		return specializationName;
	}
	public void setSpecializationName(String specializationName) {
		this.specializationName = specializationName;
	}
}
