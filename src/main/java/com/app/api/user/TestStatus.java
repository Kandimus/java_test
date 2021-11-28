package com.testjava.app.api.user;

import lombok.Data;

@Data
class TestStatus
{
	public TestStatus() {}
	public TestStatus(String s) { status = s; }

	String status;
}
