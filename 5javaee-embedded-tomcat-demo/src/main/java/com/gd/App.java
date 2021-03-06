package com.gd;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class App {

	public static void main(String[] args) throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir("temp");
		// Solution 1
		// tomcat.setPort(8080);
		// tomcat.getConnector();c

		// Solution 2, make server listen on 2 ports
		Connector connector1 = tomcat.getConnector();
		connector1.setPort(8080);
		Connector connector2 = new Connector();
		connector2.setPort(8090);
		String contextPath = "";
		String docBase = new File(".").getAbsolutePath();

		Context context = tomcat.addContext(contextPath, docBase);

		class SampleServlet extends HttpServlet {

			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
				PrintWriter writer = resp.getWriter();

				writer.println("<html><title>Welcome</title><body>");
				writer.println("<h1>Have a Great Day!</h1>");
				writer.println("</body></html>");
			}
		}

		String servletName = "SampleServlet";
		String urlPattern = "/aa";

		tomcat.addServlet(contextPath, servletName, new SampleServlet());
		context.addServletMappingDecoded(urlPattern, servletName);

		tomcat.start();
		tomcat.getService().addConnector(connector1);
		tomcat.getService().addConnector(connector2);
		tomcat.getServer().await();
	}
}