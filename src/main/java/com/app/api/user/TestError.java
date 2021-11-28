package com.testjava.app.api.user;

import lombok.Data;

@Data
class TestError
{
	public TestError() {}
	public TestError(String err) { error = err; }

	String error;
}
