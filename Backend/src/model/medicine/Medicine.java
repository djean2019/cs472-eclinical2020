package model.medicine;

public class Medicine {
	private int medicineId;
	private String medicineName;
	private MedicineType medicineType;
	public Medicine() {}
	public Medicine(int medicineId, String medicineName, MedicineType medicineType) {
		super();
		this.medicineId = medicineId;
		this.medicineName = medicineName;
		this.medicineType = medicineType;
	}
	public Medicine(String medicineName, MedicineType medicineType) {
		super();
		this.medicineName = medicineName;
		this.medicineType = medicineType;
	}
	public int getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public MedicineType getMedicineType() {
		return medicineType;
	}
	public void setMedicineType(MedicineType medicineType) {
		this.medicineType = medicineType;
	}
}
