package com.testjava.app.api.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.testjava.data.SqlDB;
import com.testjava.data.TestConst;
import com.testjava.app.api.user.UserMessage;
import com.testjava.app.api.user.HistoryMessage;
import com.testjava.app.api.user.TestError;
import com.testjava.app.api.user.TestStatus;

public class MessageHandle implements HttpHandler
{
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		String requestParamValue = "";

		if("GET".equals(exchange.getRequestMethod())) {
			requestParamValue = mapper.writeValueAsString(new TestError("Error request method!"));

		} else if("POST".equals(exchange.getRequestMethod())) {
			String bearer = exchange.getRequestHeaders().getFirst(TestConst.AUTH());

			if (bearer.length() > 7) {
				String[] bearerSplit = bearer.split(" ");

				if (bearerSplit.length == 2) {
					requestParamValue = doPost(exchange.getRequestBody(), bearerSplit[1]);
				}
			}
		}

		exchange.sendResponseHeaders(200, requestParamValue.length());

		OutputStream os = exchange.getResponseBody();
		os.write(requestParamValue.getBytes());
		os.flush();
		os.close();
	}

	private String doPost(InputStream is, String jwt_str)
	{
		ObjectMapper mapper = new ObjectMapper();

		try {
			UserMessage msg = mapper.readValue(is, UserMessage.class);

			int user_id = 0;

			try {
				JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TestConst.SECRET())).build();
				DecodedJWT decodejwt = verifier.verify(jwt_str);

				user_id = decodejwt.getClaim(TestConst.USER_ID()).asInt();
			}  catch (JWTVerificationException e) {
				return mapper.writeValueAsString(new TestError("Error decode JWT"));
			}

			if (user_id < 1) {
				return mapper.writeValueAsString(new TestError("Fault jwt"));
			}

			SqlDB sql = new SqlDB();

			if (msg.getMessage().equals("history 10")) {
				HistoryMessage history = new HistoryMessage();

				history.setMessages(sql.getHstory());

				return mapper.writeValueAsString(history);
			}

			sql.addMessage(user_id, msg.getName(), msg.getMessage());
			return mapper.writeValueAsString(new TestStatus("success"));

		} catch(Exception e){
			return "";
		}
	}
}
