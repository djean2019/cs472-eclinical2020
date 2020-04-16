package controller.medicine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.Response;
import model.medicine.Medicine;
import model.medicine.MedicineType;
import repository.medicine.MedicineRepository;

@WebServlet("/api/medicine")
@MultipartConfig
public class MedicineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MedicineRepository medicineRepo = MedicineRepository.getInstance();
    private List<Medicine> medicines=new ArrayList<Medicine>();
    private Gson gson = new GsonBuilder().create();
    
    public MedicineController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Response res = new Response();
		try {
			medicines = medicineRepo.loadMedicines();
			res = new Response("succeed", 200, medicines);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, medicines);
		}
		response.getWriter().print(gson.toJson(res));

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			String medicineName = request.getParameter("medicine_name");
			int medTypeId = Integer.parseInt(request.getParameter("medicine_type_id"));

			MedicineType medicineType = new MedicineType(medTypeId);
			
			Medicine medicine = new Medicine(medicineName,medicineType);
			Boolean isSuccess = medicineRepo.saveMedicine(medicine);

			if(isSuccess)
				res = new Response("succeed", 200, null);
			else
				res = new Response("failed", 500, null);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, null);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			int medicineId = Integer.parseInt(request.getParameter("medicine_id"));
			String medicineName = request.getParameter("medicine_name");
			MedicineType medicineType = new MedicineType(Integer.parseInt(request.getParameter("medicine_type_id")));
			Medicine medicine =new Medicine(medicineId,medicineName,medicineType);
			
			boolean isSuccess = medicineRepo.updateMedicineById(medicine);
			if(isSuccess)
				res = new Response("succeed", 200, null);
			else
				res = new Response("failed", 500, null);
		}catch(Exception ex) {
			ex.printStackTrace();
			res = new Response(ex.getMessage(), 500, null);
		}
		response.getWriter().print(gson.toJson(res));		
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			int medicineId = Integer.parseInt(request.getParameter("medicine_id"));
			boolean isSuccess = medicineRepo.deleteMedicineById(medicineId);
			if(isSuccess)
				res = new Response("succeed", 200, null);
			else
				res = new Response("failed", 500, null);
		}catch(Exception ex) {
			ex.printStackTrace();
			res = new Response(ex.getMessage(), 500, null);
		}
		response.getWriter().print(gson.toJson(res));
	}

}
