package controller.user;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.Response;
import model.user.User;
import repository.user.UserRepository;

@WebServlet("/api/userlogincontroller")
@MultipartConfig
public class UserLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserRepository userRepo = UserRepository.getInstance();
	private Gson gson = new GsonBuilder().create();
	
    public UserLoginController() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = userRepo.login(username, password);
			if(user != null)
				res = new Response("succeed", 200, Arrays.asList(user));
			else
				res = new Response("failed", 500, null);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, null);
		}
		response.getWriter().print(gson.toJson(res));
	}
}
