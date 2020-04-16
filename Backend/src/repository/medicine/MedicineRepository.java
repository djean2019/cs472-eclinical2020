package repository.medicine;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Database;
import model.medicine.Medicine;
import model.medicine.MedicineType;

public class MedicineRepository {
	private static MedicineRepository medicineRepository;
	private Database database = Database.getInstance();
	private MedicineRepository() {}
	public static MedicineRepository getInstance() {
		if(medicineRepository == null)
			medicineRepository = new MedicineRepository();
		return medicineRepository;
	}
	public boolean saveMedicine(Medicine medicine) {
		boolean isSuccess = false;
		try {
			if(medicine != null) {
				database.executeStatement("INSERT into medicine(medicinename, medicinetypeid) VALUES(?, ?)", 
													  Arrays.asList(medicine.getMedicineName(), medicine.getMedicineType().getMedicineTypeId()));
				isSuccess = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public List<Medicine> loadMedicines() {
		List<Medicine> medicines = new ArrayList<>();
		MedicineTypeRepository medicineTypeRepo = MedicineTypeRepository.getInstance();
		try {
			ResultSet medicineResult = database.getResult("SELECT * FROM medicine", null);
			while(medicineResult.next()) {
				MedicineType medicineType = medicineTypeRepo.loadMedicineTypeById(medicineResult.getInt("medicinetypeid"));
				medicines.add(new Medicine(medicineResult.getInt(1), medicineResult.getString(2), medicineType));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return medicines;
	}
	public Medicine loadMedicineById(int medicineId) {
		Medicine medicines = new Medicine();
		MedicineTypeRepository medicineTypeRepo = MedicineTypeRepository.getInstance();
		try {
			ResultSet medicineResult = database.getResult("SELECT * FROM medicine WHERE medicineid = ?", Arrays.asList(medicineId));
			if(medicineResult.next()) {
				MedicineType medicineType = medicineTypeRepo.loadMedicineTypeById(medicineResult.getInt("medicinetypeid"));
				medicines = new Medicine(medicineResult.getInt(1), medicineResult.getString(2), medicineType);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return medicines;
	}
	public boolean deleteMedicineById(int medicineId) {
		boolean isSuccess = false;
		try {
			database.executeStatement("DELETE FROM medicine where medicineid = ?", Arrays.asList(medicineId));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
	public boolean updateMedicineById(Medicine medicine) {
		boolean isSuccess = false;
		try {
			database.executeStatement("UPDATE medicine SET medicinename = ?, medicinetypeid = ? WHERE medicineid = ?", 
												   Arrays.asList(medicine.getMedicineName(), 
																 medicine.getMedicineType().getMedicineTypeId(),
																 medicine.getMedicineId()));
			isSuccess = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return isSuccess;
	}
}
