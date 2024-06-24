package tabling;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import tabling.server.CategoryHandler;
import tabling.server.CustomerHandler;
import tabling.server.LikeHandler;
import tabling.server.LocationHandler;
import tabling.server.MenuHandler;
import tabling.server.ReservationHandler;
import tabling.server.RestaurantHandler;


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
		httpServer.createContext("/location", new LocationHandler());
		httpServer.createContext("/reservation", new ReservationHandler());
		httpServer.createContext("/like", new LikeHandler());
		httpServer.createContext("/restaurant", new RestaurantHandler());
		httpServer.createContext("/menu", new MenuHandler());
	}

}
