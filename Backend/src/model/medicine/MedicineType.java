package model.medicine;

public class MedicineType {
	private int medicineTypeId;
	private String medicineTypeName;
	public MedicineType() {}
	public MedicineType(int medicineTypeId, String medicineTypeName) {
		super();
		this.medicineTypeId = medicineTypeId;
		this.medicineTypeName = medicineTypeName;
	}
	public MedicineType(int medicineTypeId) {
		super();
		this.medicineTypeId = medicineTypeId;
	}
	public MedicineType(String medicineTypeName) {
		super();
		this.medicineTypeName = medicineTypeName;
	}
	public int getMedicineTypeId() {
		return medicineTypeId;
	}
	public void setMedicineTypeId(int medicineTypeId) {
		this.medicineTypeId = medicineTypeId;
	}
	public String getMedicineTypeName() {
		return medicineTypeName;
	}
	public void setMedicineTypeName(String medicineTypeName) {
		this.medicineTypeName = medicineTypeName;
	}
}
