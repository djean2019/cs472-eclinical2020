package controller.doctorspecialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.Response;
import model.doctorpatient.DoctorSpecialization;
import repository.patientdoctor.DoctorSpecializationRepository;

@WebServlet("/api/doctorspecializationcontroller")
public class DoctorSpecializationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DoctorSpecializationRepository doctorSpecializationRepo = DoctorSpecializationRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public DoctorSpecializationController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<DoctorSpecialization> doctorSpecializations = new ArrayList<DoctorSpecialization>();
		Response res = new Response();
		try {
			doctorSpecializations = doctorSpecializationRepo.loadDoctorSpecializations();
			res = new Response("succeed", 200, doctorSpecializations);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, doctorSpecializations);
		}
		response.getWriter().print(gson.toJson(res));
	}
}
