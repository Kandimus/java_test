package com.testjava.app.api.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.testjava.data.SqlDB;
import com.testjava.app.api.user.UserRequest;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageHandle implements HttpHandler
{
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		String requestParamValue = "Error request method!";

		if("GET".equals(exchange.getRequestMethod())) {
			;
		} else if("POST".equals(exchange.getRequestMethod())) {
			String bearer = exchange.getRequestHeaders().getFirst("Authorization");

			if (bearer.length() > 7) {
				String[] bearerSplit = bearer.split(" ");

				if (bearerSplit.length == 2) {
					System.out.println("jwt: [" + bearerSplit[1] + "]");
					requestParamValue = doPost(exchange.getRequestBody(), bearerSplit[1]);
				}
			}
		}

		exchange.sendResponseHeaders(200, requestParamValue.length());

		System.out.println("result: [" + requestParamValue + "]");
		OutputStream os = exchange.getResponseBody();
		os.write(requestParamValue.getBytes());
		os.flush();
		os.close();
	}

	private String doPost(InputStream is, String jwt)
	{
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			UserRequest user = mapper.readValue(is, UserRequest.class);
//			SqlDB sql = new SqlDB();

//			if (sql.checkRegistration(user.getLogin(), user.getPassword())) {
//				return "OK";
//			}
//		} catch(Exception e){
//			System.out.println(e.getMessage());
//		}

		return "ERROR";
	}
}
