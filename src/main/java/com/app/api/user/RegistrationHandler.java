package com.testjava.app.api.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.testjava.data.SqlDB;
import com.testjava.app.api.user.UserRequest;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class RegistrationHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		String requestParamValue = "";

		if("GET".equals(exchange.getRequestMethod())) {
			requestParamValue = "Error request method!";
		} else if("POST".equals(exchange.getRequestMethod())) {
			requestParamValue = doPost(exchange.getRequestBody());
		}

		exchange.sendResponseHeaders(200, requestParamValue.length());

		System.out.println("result: [" + requestParamValue + "]");
		OutputStream os = exchange.getResponseBody();
		os.write(requestParamValue.getBytes());
		os.flush();
		os.close();
	}

	private String doPost(InputStream is)
	{
		try {
			ObjectMapper mapper = new ObjectMapper();
			UserRequest user = mapper.readValue(is, UserRequest.class);
			SqlDB sql = new SqlDB();

			int user_id = sql.checkRegistration(user.getName(), user.getPassword());
			if (user_id != 0) {

				JWTCreator.Builder builder = JWT.create()
						   .withClaim("account", user_id);

				return String.format("{\"jwt_token\":\"%s\"}", builder.sign(Algorithm.HMAC256("sdfasdfasdfasdfasdfasdfasdfa")));
			}
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

		return "{\"error\":\"bad user name or password.\"}";
	}
}
