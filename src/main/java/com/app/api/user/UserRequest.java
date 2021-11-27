package com.testjava.app.api.user;

import lombok.Data;

@Data
class UserRequest
{
	public UserRequest() {}
	String name;
	String password;
}
