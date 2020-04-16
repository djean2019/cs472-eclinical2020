package controller.checkinout;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.Response;
import model.appointment.Appointment;
import model.checkinout.CheckInOut;
import model.doctorpatient.Person;
import repository.checkinout.CheckInOutRepository;

@WebServlet("/api/checkinoutcontroller")
public class CheckInOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CheckInOutRepository checkInOutRepo = CheckInOutRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public CheckInOutController() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			Appointment appointment = new Appointment(Integer.parseInt(request.getParameter("appointment_id")));
			Person doctor = new Person(Integer.parseInt(request.getParameter("doctor_id")));
			LocalDateTime checkInDateTime = LocalDateTime.parse(request.getParameter("checkin_datetime"));
			LocalDateTime checkOutDateTime = LocalDateTime.parse(request.getParameter("checkout_datetime"));
			double charge = Double.parseDouble(request.getParameter("charge"));
			String description = request.getParameter("description");
			boolean isSuccess = checkInOutRepo.saveCheckInOut(new CheckInOut(appointment, doctor, checkInDateTime, checkOutDateTime, charge, description));
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
