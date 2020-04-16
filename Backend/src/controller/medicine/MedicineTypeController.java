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
import model.medicine.MedicineType;
import repository.medicine.MedicineTypeRepository;

/**
 * Servlet implementation class MedicineTypeController
 */
@WebServlet("/api/medicinetype")
@MultipartConfig
public class MedicineTypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      private MedicineTypeRepository medicineTypeRepo = MedicineTypeRepository.getInstance();
      private List<MedicineType> medicineTypes=new ArrayList<MedicineType>();
      private Gson gson = new GsonBuilder().create();
   
      public MedicineTypeController() {
        super();
    }
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Response res = new Response();
		try {
			medicineTypes = medicineTypeRepo.loadMedicineType();
			res = new Response("succeed", 200, medicineTypes);
		}catch(Exception e) {
			res = new Response(e.getMessage(), 500, medicineTypes);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			String medicineTypeName = request.getParameter("medicine_type_name");
			MedicineType medicineType = new MedicineType(medicineTypeName);
			
			boolean isSuccess = medicineTypeRepo.saveMedicineType(medicineType);
			System.out.println(isSuccess);
			if(isSuccess)
				res = new Response("succeed", 200, null);
			else
				res = new Response("failed", 500, null);
		}catch(Exception e) {
			res = new Response(e.getMessage(), 500, null);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Response res = new Response();
		try {
			int medicineTypeId = Integer.parseInt(request.getParameter("medicine_type_id"));
			String medicineTypeName = request.getParameter("medicine_type_name");
			MedicineType medicineType = new MedicineType(medicineTypeId,medicineTypeName);
			
			boolean isSuccess = medicineTypeRepo.updateMedicineTypeById(medicineType);
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
			int medicineTypeId = Integer.parseInt(request.getParameter("medicine_type_id"));
			boolean isSuccess = medicineTypeRepo.deleteMedicineTypeById(medicineTypeId);
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
