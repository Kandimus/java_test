package com.testjava.app.api.user;

import lombok.Data;

@Data
class TestJWT
{
	public TestJWT(String t) { jwt_token = t; }

	String jwt_token;
}
