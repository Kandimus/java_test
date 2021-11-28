package com.testjava.app.api.user;

import lombok.Data;

@Data
class UserMessage
{
	public UserMessage() {}

	String name;
	String message;
}
