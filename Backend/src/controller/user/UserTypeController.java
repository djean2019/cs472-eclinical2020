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
import model.user.UserType;
import repository.user.UserTypeRepository;

@WebServlet("/api/usertypecontroller")
@MultipartConfig
public class UserTypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserTypeRepository userTypeRepo = UserTypeRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public UserTypeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<UserType> userType = new ArrayList<UserType>();
		Response res = new Response();
		try {
			userType = userTypeRepo.loadUserTypes();
			res = new Response("succeed", 200, userType);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, userType);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			String userTypeName = request.getParameter("user_type_name");
			boolean isSuccess = userTypeRepo.saveUserType(new UserType(userTypeName));	
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
			int userTypeId = Integer.parseInt(request.getParameter("user_type_id"));
			String userTypeName = request.getParameter("user_type_name");
			boolean isSuccess = userTypeRepo.updateUserTypeById(new UserType(userTypeId, userTypeName));
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
			int userTypeId = Integer.parseInt(request.getParameter("user_type_id"));
			boolean isSuccess = userTypeRepo.deleteUserTypeById(userTypeId);
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
