package controller.desease;

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
import model.desease.DeseaseCategory;
import repository.desease.DeseaseCategoryRepository;

@WebServlet("/api/deseasecategory")
@MultipartConfig
public class DeseaseCategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private DeseaseCategoryRepository deseaseCategoryRepo = DeseaseCategoryRepository.getInstance();
      private List<DeseaseCategory> deseaseCategories=new ArrayList<DeseaseCategory>();
      private Gson gson = new GsonBuilder().create();
    
    public DeseaseCategoryController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			deseaseCategories = deseaseCategoryRepo.loadDeseaseCategory();
			res = new Response("succeed", 200, deseaseCategories);
		}catch(Exception e) {
			res = new Response(e.getMessage(), 500, deseaseCategories);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			String deseaseCategoryName = request.getParameter("desease_type_name");
			DeseaseCategory deseaseCategory = new DeseaseCategory(deseaseCategoryName);
			
			boolean isSuccess = deseaseCategoryRepo.saveDeseaseCategory(deseaseCategory);
			System.out.println(isSuccess);
			if(isSuccess)
				res = new Response("succeed", 200, null);
			else
				res = new Response("failed", 500, null);
		}catch(Exception e) {
			res = new Response(e.getMessage(), 500, null);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			int deseaseCategoryId = Integer.parseInt(request.getParameter("desease_type_id"));
			String deseaseCategoryName = request.getParameter("desease_type_name");
			DeseaseCategory deseaseCategory = new DeseaseCategory(deseaseCategoryId,deseaseCategoryName);
			
			boolean isSuccess = deseaseCategoryRepo.updateDeseaseCategoryById(deseaseCategory);
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
			int deseaseCategoryId = Integer.parseInt(request.getParameter("desease_type_id"));
			boolean isSuccess = deseaseCategoryRepo.deleteDeseaseCategoryById(deseaseCategoryId);
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
