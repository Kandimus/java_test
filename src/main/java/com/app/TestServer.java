package com.app;

import com.testjava.app.api.user.RegistrationHandler;
import com.testjava.app.api.user.MessageHandle;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class TestServer {

	public static void main(String[] args) throws IOException
	{
		int serverPort = 8000;
		HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

		server.createContext("/api/users/registration", new RegistrationHandler());
		server.createContext("/api/messages", new MessageHandle());

		server.setExecutor(null);
		server.start();
    }
}
