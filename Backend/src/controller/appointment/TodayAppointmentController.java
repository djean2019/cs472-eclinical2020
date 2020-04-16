package controller.appointment;

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
import model.appointment.Appointment;
import repository.appointment.AppointmentRepository;

@WebServlet("/api/todayappointmentcontroller")
public class TodayAppointmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public TodayAppointmentController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Appointment> appointments = new ArrayList<Appointment>();
		Response res = new Response();
		try {
			appointments = appointmentRepo.loadTodayAppointments();
			res = new Response("succeed", 200, appointments);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, appointments);
		}
		response.getWriter().print(gson.toJson(res));
	}
}
