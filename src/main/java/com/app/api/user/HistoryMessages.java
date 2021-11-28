package com.testjava.app.api.user;

import java.util.ArrayList;

import lombok.Data;

@Data
class HistoryMessage
{
	public HistoryMessage() {}

	ArrayList<String> messages;
}
