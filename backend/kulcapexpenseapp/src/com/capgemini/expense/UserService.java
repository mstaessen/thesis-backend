package com.capgemini.expense;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.datanucleus.util.StringUtils;

import com.capgemini.expense.model.Employee;
import com.capgemini.expense.model.ExpenseContext;

@Path("/userService")
public class UserService {

	@Context
	private ServletContext context;

	private static final Set<Employee> employeeSet = new HashSet<Employee>();
	static {
		Employee emp1 = new Employee(1, "test123", "Michiel", "Staessen", "michiel.staessen@student.kuleuven.be", "4203", 1, "user");
		Employee emp2 = new Employee(2, "test123", "Jan", "Verhulst", "jan.verhulst@capgemini.com", "4204", 2, "admin");
		Employee emp3 = new Employee(3, "test123", "Sander", "Vann Loock", "sander.vanloock1@student.kuleuven.be‎", "4205", 3, "user");
		Employee emp4 = new Employee(4, "test123", "Tim", "Ameye", "tim.ameye@student.kuleuven.be", "4206", 4, "user");
		Employee emp5 = new Employee(5, "test123", "Bert", "Outtier", "bert.outtier@student.kuleuven.be", "4207", 5, "user");
		Employee emp6 = new Employee(6, "test123", "Gonzalo", "Parra", "gonzalo.parra@cs.kuleuven.be‎", "4208", 6, "user");
		Employee emp7 = new Employee(7, "test123", "Erik", "Duval", " ‎erik.duval@cs.kuleuven.be‎", "4208", 7, "user");
		Employee emp8 = new Employee(8, "test123", "Jannik", "Persoons", "‎jannik.persoons@capgemini.com", "4208", 8, "admin");

		employeeSet.add(emp1);
		employeeSet.add(emp2);
		employeeSet.add(emp3);
		employeeSet.add(emp4);
		employeeSet.add(emp5);
		employeeSet.add(emp6);
		employeeSet.add(emp7);
		employeeSet.add(emp8);
	}
	
	@OPTIONS
    @Path("/login")
    public void login(@FormParam("email") String email, @FormParam("password") String password, @Context HttpServletResponse response, @Context HttpServletRequest req) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers","X-Requested-With");
    }

	@POST
	@Path("/login")
	public String login(@FormParam("email") String email, @FormParam("password") String password, @Context HttpServletResponse response) {
		String token = null;
		response.setHeader("Access-Control-Allow-Origin", "*");
		Iterator<Employee> it = employeeSet.iterator();
		while (it.hasNext()) {
			Employee emp = it.next();
			if (StringUtils.areStringsEqual(emp.getEmail(), email) && StringUtils.areStringsEqual(emp.getPassword(), password)) {
				token = UUID.randomUUID().toString();
				context.setAttribute(token, new ExpenseContext(emp, new Date()));
				return token;
			}
		}
		
		return token;

	}
	
	@OPTIONS
    @Path("/logout")
    public void logout(@FormParam("token") String token, @Context HttpServletResponse response, @Context HttpServletRequest req) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers","X-Requested-With");
    }

	@POST
	@Path("/logout")
	public void logout(@FormParam("token") String token, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		context.removeAttribute(token);
	}
	
	@OPTIONS
    @Path("/getEmployee")
    public void getEmployee(@FormParam("token") String token, @Context HttpServletResponse response, @Context HttpServletRequest req) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers","X-Requested-With");
    }

	@POST
	@Path("/getEmployee")
	@Produces("application/json")
	public Employee getEmployee(@FormParam("token") String token, @Context HttpServletResponse response) {
		ExpenseContext expContext = TokenUtil.accessToken(context, token);
		response.setHeader("Access-Control-Allow-Origin", "*");
		if (expContext != null) {
			return expContext.getEmployee();
		}
		return null;
	}

}
