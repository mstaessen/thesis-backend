package com.capgemini.expense;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.capgemini.expense.dao.Store;
import com.capgemini.expense.model.Errors;
import com.capgemini.expense.model.ExpenseContext;
import com.capgemini.expense.model.ExpenseForm;
import com.capgemini.expense.model.ExpenseRequest;
import com.capgemini.expense.model.Suggestions;
import com.itextpdf.text.DocumentException;

@Path("/expenseService")
public class ExpenseService {

	@Context
	private ServletContext context;

	private static Set<String> projectCodes = new HashSet<String>();
	private final static int MAX_SUGGESTIONS = 7;
	static {
		projectCodes.add("G20AZER");
		projectCodes.add("G20ARRRR");
		projectCodes.add("G20BEEEE");
		projectCodes.add("G20AZERRR");
		projectCodes.add("G20AZERIII");
		projectCodes.add("G35AZER");
		projectCodes.add("G35ARRRR");
		projectCodes.add("G35BEEE");
		projectCodes.add("G35AZERRR");
		projectCodes.add("G35BEEZ");
		projectCodes.add("G35AZERTTT");
		projectCodes.add("G35AZERPPP");
		projectCodes.add("G35AZERDDDD");
		projectCodes.add("G35FFFFF");
		projectCodes.add("G35TTTT");
		projectCodes.add("G35EEEEE");
	}

	@POST
	@Path("/getProjectCodeSuggestion")
	@Produces("application/json")
	public Suggestions getProjectCodeSuggestion(@FormParam("keyword") String keyword) {
		List<String> suggestions = new ArrayList<String>();
		int nrOfSuggestions = 0;
		Iterator<String> it = projectCodes.iterator();
		while (it.hasNext() && nrOfSuggestions < MAX_SUGGESTIONS) {
			String code = it.next();
			if (code.contains(keyword)) {
				nrOfSuggestions++;
				suggestions.add(code);
			}
		}

		Suggestions results = new Suggestions();
		results.setData(suggestions);
		Collections.sort(suggestions);
		return results;
	}

	@POST
	@Path("/saveExpense")
	@Consumes("application/json")
	@Produces("application/json")
	public Errors saveExpense(ExpenseRequest expenseRequest, @Context HttpServletResponse response) {
		ExpenseContext expContext = TokenUtil.accessToken(context, expenseRequest.getToken());
		if (expContext != null) {
			if (expenseRequest.getExpenseForm() == null) {
				Response.status(401);
			} else {
				Errors errors = expenseRequest.getExpenseForm().validate();
				if (errors != null) {
					response.setStatus(200);
					return errors;
				} else {
					Store.getInstance().storeExpenseForm(expenseRequest.getExpenseForm(), expContext.getEmployee());
				}
				response.setStatus(200);
			}
		} else {
			response.setStatus(401);
		}
		return null;

	}

	@POST
	@Path("/getExpenseForms")
	public List<ExpenseForm> getExpenseForms(@FormParam("token") String token) {
		ExpenseContext expContext = TokenUtil.accessToken(context, token);
		if (expContext != null) {
			if (expContext.getEmployee().getRole().equals("admin")) {
				return Store.getInstance().getAll();
			} else {
				return Store.getInstance().getByEmployeeId(expContext.getEmployee().getId());
			}
		}
		return null;
	}

	@POST
	@Path("/getExpenseFormPDF")
	public void getExpenseFormPDF(@FormParam("token") String token, @FormParam("expenseFormId") int expenseFormId, @Context HttpServletResponse response) {
		ExpenseContext expContext = TokenUtil.accessToken(context, token);
		if (expContext != null) {
			ExpenseForm form = Store.getInstance().getExpenseFormById(expenseFormId);
			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Disposition", "inline;filename=ExpenseNote_" + expContext.getEmployee().getLastName() + "_"
					+ expContext.getEmployee().getFirstName() + "_" + expContext.getEmployee().getEmployeeNumber() + "_" + sdf.format(form.getDate()) + ".pdf");
			try {
				PDFUtil.generatePdf(form, expContext.getEmployee(), response.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@POST
	@Path("/changeState")
	public void changeState(@FormParam("token") String token, @FormParam("expenseFormId") int expenseFormId, @FormParam("statusId") int statusId) {
		ExpenseContext expContext = TokenUtil.accessToken(context, token);
		if (expContext != null && expContext.getEmployee().getRole().equals("admin")) {
			Store.getInstance().saveState(expenseFormId, statusId);
		}
	}

}
