package com.ubdynamics.testapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.ContactInfo;
import db.Employee;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public HelloWorld() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Employee> filteredEmployees = new Employee().find("name=?",
				"Jesse").fetch();

		for (Employee employee : filteredEmployees) {
			employee.delete();
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		Integer age = Integer.parseInt(request.getParameter("age"));

		Employee employee = new Employee();
		employee.name = name;
		employee.age = age;
		employee.contactInfos = new ArrayList<ContactInfo>();
		employee.save();

		List<Employee> employees = new Employee().find().fetch();

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<title>/HelloWorld</title>");

		for (Employee emp : employees) {

			out.println("<p>" + emp.name + "</p>");
		}

	}

}
