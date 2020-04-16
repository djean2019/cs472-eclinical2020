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
import model.desease.Desease;
import model.desease.DeseaseCategory;
import repository.desease.DeseaseRepository;

@WebServlet("/api/desease")
@MultipartConfig
public class DeseaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private DeseaseRepository deseaseRepo = DeseaseRepository.getInstance();
	    private List<Desease> deseases=new ArrayList<Desease>();
	    private Gson gson = new GsonBuilder().create();
	   
    public DeseaseController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			deseases = deseaseRepo.loadDeseases();
			res = new Response("succeed", 200, deseases);
		}catch(Exception ex) {
			res = new Response(ex.getMessage(), 500, deseases);
		}
		response.getWriter().print(gson.toJson(res));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Response res = new Response();
		try {
			String deseaseName = request.getParameter("desease_name");
			int desTypeId = Integer.parseInt(request.getParameter("desease_type_id"));

			DeseaseCategory deseaseCategory = new DeseaseCategory(desTypeId);
			
			Desease desease = new Desease(deseaseName,deseaseCategory);
			Boolean isSuccess = deseaseRepo.saveDesease(desease);

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
			int deseaseId = Integer.parseInt(request.getParameter("desease_id"));
			String deseaseName = request.getParameter("desease_name");
			DeseaseCategory deseaseCategory = new DeseaseCategory(Integer.parseInt(request.getParameter("desease_type_id")));
			Desease desease =new Desease(deseaseId,deseaseName,deseaseCategory);
			
			boolean isSuccess = deseaseRepo.updateDeseaseById(desease);
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
			int deseaseId = Integer.parseInt(request.getParameter("desease_id"));
			boolean isSuccess = deseaseRepo.deleteDeseaseById(deseaseId);
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
