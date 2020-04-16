package controller.doctor;

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
import model.doctorpatient.DoctorSpecialization;
import model.doctorpatient.Person;
import model.doctorpatient.Specialization;
import repository.patientdoctor.DoctorRepository;
import repository.patientdoctor.DoctorSpecializationRepository;

@WebServlet("/api/doctorcontroller")
@MultipartConfig
public class DoctorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DoctorRepository doctorRepo = DoctorRepository.getInstance();
	private DoctorSpecializationRepository doctorSpecializationRepo = DoctorSpecializationRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public DoctorController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Person> doctors = new ArrayList<Person>();
		Response res = new Response();
		try {
			doctors = doctorRepo.loadDoctors();
			res = new Response("succeed", 200, doctors);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, doctors);
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
			int doctorId = doctorRepo.saveDoctor(new Person(firstName, middleName, lastName, contactPhone, address));
			String[] specializations = request.getParameterValues("specializations");
			for(String sp: specializations)
				doctorSpecializationRepo.saveDoctorSpecialization(new DoctorSpecialization(new Specialization(Integer.parseInt(sp)), new Person(doctorId)));
			if(doctorId != 0)
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
			int doctorId = Integer.parseInt(request.getParameter("doctor_id"));
			String firstName = request.getParameter("first_name");
			String middleName = request.getParameter("middle_name");
			String lastName = request.getParameter("last_name");
			String contactPhone = request.getParameter("contact_phone");
			String address = request.getParameter("address");
			boolean isSuccess = doctorRepo.updateDoctorById(new Person(doctorId, firstName, middleName, lastName, contactPhone, address));
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
			int doctorId = Integer.parseInt(request.getParameter("doctor_id"));
			boolean isSuccess = doctorRepo.deleteDoctorById(doctorId);
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
