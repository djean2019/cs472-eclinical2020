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
import repository.appointment.AppointmentRepository;

@WebServlet("/api/appointmentavailibilitycontroller")
@MultipartConfig
public class AppointmentAvailibilityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public AppointmentAvailibilityController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<LocalTime> availableTimes = new ArrayList<LocalTime>();
		Response res = new Response();
		try {
			availableTimes = appointmentRepo.checkAvailability(LocalDate.parse(request.getParameter("date")));
			res = new Response("succeed", 200, availableTimes);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, availableTimes);
		}
		response.getWriter().print(gson.toJson(res));
	}
}
