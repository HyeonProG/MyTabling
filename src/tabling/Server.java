package tabling;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import tabling.frame.CategoryFrame;
import tabling.server.CategoryHandler;
import tabling.server.CustomerHandler;
import tabling.server.ReservationHandler;


public class Server {

	public static void main(String[] args) {
		try {
			HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
			
			setCreateContext(httpServer);
			httpServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void setCreateContext(HttpServer httpServer) {
		httpServer.createContext("/customer", new CustomerHandler());
		httpServer.createContext("/category", new CategoryHandler());
		httpServer.createContext("/reservation", new ReservationHandler());
	}

}
