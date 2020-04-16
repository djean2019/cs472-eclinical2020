package controller.appointment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
import model.appointment.Appointment;
import model.doctorpatient.Specialization;
import model.user.User;
import repository.appointment.AppointmentRepository;

@WebServlet("/api/appointmentcontroller")
@MultipartConfig
public class AppointmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public AppointmentController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Appointment> appointments = new ArrayList<Appointment>();
		Response res = new Response();
		try {
			appointments = appointmentRepo.loadAppointments();
			res = new Response("succeed", 200, appointments);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, appointments);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			LocalDate appointmentDate = LocalDate.parse(request.getParameter("appointment_date"));
			LocalTime appointmentTime = LocalTime.parse(request.getParameter("appointment_time"));
			Specialization specialization = new Specialization(Integer.parseInt(request.getParameter("specialization_id")));
			User appointedBy = new User(Integer.parseInt(request.getParameter("user_id")));
			boolean isSuccess = appointmentRepo.saveAppointmentRepository(new Appointment(appointmentDate, appointmentTime, specialization, appointedBy));
			if(isSuccess)
				res = new Response("succeed", 200, null);
			else
				res = new Response("failed", 500, null);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, null);
		}
		response.getWriter().print(gson.toJson(res));
	}
}
