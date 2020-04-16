package controller.specialization;

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
import model.doctorpatient.Specialization;
import repository.patientdoctor.SpecializationRepository;

@WebServlet("/api/specializationcontroller")
@MultipartConfig
public class SpecializationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SpecializationRepository specializationRepo = SpecializationRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
    
    public SpecializationController() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Specialization> specializations = new ArrayList<Specialization>();
		Response res = new Response();
		try {
			specializations = specializationRepo.loadSpecializations();
			res = new Response("succeed", 200, specializations);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, specializations);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			String specializationName = request.getParameter("specialization_name");
			boolean isSuccess = specializationRepo.saveSpecialization(new Specialization(specializationName));
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
			int specializationId = Integer.parseInt(request.getParameter("specialization_id"));
			String specializationName = request.getParameter("specialization_name");
			boolean isSuccess = specializationRepo.updateSpecializationById(new Specialization(specializationId, specializationName));
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
			int specialization_id = Integer.parseInt(request.getParameter("specialization_id"));
			boolean isSuccess = specializationRepo.deleteSpecializationById(specialization_id);
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
