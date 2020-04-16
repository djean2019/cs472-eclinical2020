package controller.patient;

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
import model.doctorpatient.Person;
import repository.patientdoctor.PatientRepository;

@WebServlet("/api/patientcontroller")
@MultipartConfig
public class PatientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PatientRepository patientRepo = PatientRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public PatientController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Person> patients = new ArrayList<Person>();
		Response res = new Response();
		try {
			patients = patientRepo.loadPatients();
			res = new Response("succeed", 200, patients);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, patients);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			String firstName = request.getParameter("first_name");
			String middleName = request.getParameter("middle_name");
			String lastName = request.getParameter("last_name");
			String contactPhone = request.getParameter("contact_phone");
			String address = request.getParameter("address");
			boolean isSuccess = patientRepo.savePatient(new Person(firstName, middleName, lastName, contactPhone, address));
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
			int patientId = Integer.parseInt(request.getParameter("patient_id"));
			String firstName = request.getParameter("first_name");
			String middleName = request.getParameter("middle_name");
			String lastName = request.getParameter("last_name");
			String contactPhone = request.getParameter("contact_phone");
			String address = request.getParameter("address");
			boolean isSuccess = patientRepo.updatePatientById(new Person(patientId, firstName, middleName, lastName, contactPhone, address));
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
			int patientId = Integer.parseInt(request.getParameter("patient_id"));
			boolean isSuccess = patientRepo.deletePatientById(patientId);
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
