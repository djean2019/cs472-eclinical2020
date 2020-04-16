package controller.user;

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
import model.user.User;
import model.user.UserType;
import repository.user.UserRepository;

@WebServlet("/api/usercontroller")
@MultipartConfig
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserRepository userRepo = UserRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public UserController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> users = new ArrayList<User>();
		Response res = new Response();
		try {
			users = userRepo.loadUsers();
			res = new Response("succeed", 200, users);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, users);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			UserType userType = new UserType(Integer.parseInt(request.getParameter("user_type_id")));
			Person person = new Person();
			if(request.getParameter("person_id") != null)
				person = new Person(Integer.parseInt(request.getParameter("person_id")));
			boolean isSuccess = userRepo.saveUser(new User(username, password, userType, person));
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
			int userid = Integer.parseInt(request.getParameter("user_id"));
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			UserType userType = new UserType();
			if(request.getParameter("user_type_id") != null)
				userType = new UserType(Integer.parseInt(request.getParameter("user_type_id")));
			boolean islock = false;
			if(request.getParameter("islock") != null)
				islock = Integer.parseInt(request.getParameter("islock")) == 0? false: true;
			Person person = new Person();
			if(request.getParameter("person_id") != null)
				person = new Person(Integer.parseInt(request.getParameter("person_id")));
			boolean isSuccess = userRepo.updateUserById(new User(userid, username, password, islock, userType, person));
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
			int userid = Integer.parseInt(request.getParameter("user_id"));
			boolean isSuccess = userRepo.deleteUserById(userid);
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
