package com.testjava.app.api.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.testjava.data.SqlDB;
import com.testjava.data.TestConst;
import com.testjava.app.api.user.UserRequest;
import com.testjava.app.api.user.TestError;
import com.testjava.app.api.user.TestStatus;
import com.testjava.app.api.user.TestJWT;


public class RegistrationHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		String requestParamValue = "";

		if("GET".equals(exchange.getRequestMethod())) {
			requestParamValue = mapper.writeValueAsString(new TestError("Error request method!"));

		} else if("POST".equals(exchange.getRequestMethod())) {
			requestParamValue = doPost(exchange.getRequestBody());
		}

		exchange.sendResponseHeaders(200, requestParamValue.length());

		OutputStream os = exchange.getResponseBody();
		os.write(requestParamValue.getBytes());
		os.flush();
		os.close();
	}

	private String doPost(InputStream is)
	{
		ObjectMapper mapper = new ObjectMapper();

		try {
			UserRequest user = mapper.readValue(is, UserRequest.class);
			SqlDB sql = new SqlDB();

			int user_id = sql.checkRegistration(user.getName(), user.getPassword());

			if (user_id != 0) {
				JWTCreator.Builder builder = JWT.create()
						   .withClaim(TestConst.USER_ID(), user_id);

				return mapper.writeValueAsString(new TestJWT(builder.sign(Algorithm.HMAC256(TestConst.SECRET()))));
			}
		} catch(Exception e){
			return "";
		}

		return "";
	}
}
