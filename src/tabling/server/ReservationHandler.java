package tabling.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import tabling.dao.CustomerReservationDAO;
import tabling.dao.ReservationDAO;
import tabling.dao.RestaurantReservationDAO;
import tabling.dto.JsonDTO;
import tabling.dto.ReservationDTO;
import tabling.dto.ReservationForRestaurantDTO;

public class ReservationHandler implements HttpHandler {

	private CustomerReservationDAO customerReservationDAO;
	private ReservationDAO reservationDAO;
	private RestaurantReservationDAO RestaurantReservationDAO;
	private Gson gson;

	public ReservationHandler() {
		customerReservationDAO = new CustomerReservationDAO();
		reservationDAO = new ReservationDAO();
		RestaurantReservationDAO = new RestaurantReservationDAO();
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String method = exchange.getRequestMethod();
		if ("GET".equalsIgnoreCase(method)) {
			// GET 요청시 여기서 동작
			handleGetRequest(exchange);
		} else if ("POST".equalsIgnoreCase(method)) {
			// POST 요청시 여기서 동작
			handlePostRequest(exchange);
		}
	}

	// GET 요청시 동작
	private void handleGetRequest(HttpExchange exchange) {
		URI uri = exchange.getRequestURI();
		String path = uri.getPath();
		String response = null;
		String[] pathSegments = path.split("/");
		if (pathSegments.length >= 4) {
			String type = pathSegments[2];
			String query = pathSegments[3];
			try {
				int customerId = 0;
				int restaurantId = 0;
				String[] pairs = query.split("&");
				for (String pair : pairs) {
					String[] keyValue = pair.split("=");
					if (keyValue[0].equalsIgnoreCase("customerId")) {
						customerId = Integer.parseInt(keyValue[1]);
					} else if (keyValue[0].equalsIgnoreCase("restaurantId")) {
						restaurantId = Integer.parseInt(keyValue[1]);
					}
				}
				ReservationDTO dto = reservationDAO.getReservationByCustomer(customerId);
				int reservationId = 0;
				if (dto != null) {
					reservationId = dto.getReservationId();
				}
				if (type.equalsIgnoreCase("check")) {
					int count = reservationDAO.checkReservation(restaurantId, reservationId);
					response = String.valueOf(count);
				} else if (type.equalsIgnoreCase("select")) {
					response = gson.toJson(dto);
				} else if (type.equalsIgnoreCase("restaurant")) {
					List<ReservationDTO> list = RestaurantReservationDAO.getReservationByRestaurantId(restaurantId);
					response = gson.toJson(list);
				} else if (type.equalsIgnoreCase("customer")) {
					List<ReservationForRestaurantDTO> list = RestaurantReservationDAO.getCustomerInfoByReservation(restaurantId);
					response = gson.toJson(list);
				}
				try {
					byte[] bytes = response.getBytes();
					exchange.setAttribute("Content-Type", "text/plain; charset=UTF-8");
					exchange.sendResponseHeaders(200, bytes.length);
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
					writer.write(response);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// POST 요청시 동작
	private void handlePostRequest(HttpExchange exchange) {
		URI uri = exchange.getRequestURI();
		String path = uri.getPath();
		String[] pathSegments = path.split("/");
		String protocol = null;
		if (pathSegments.length >= 3) {
			protocol = pathSegments[2];
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		String response = null;
		String inputLine;
		StringBuffer bufferStr = new StringBuffer();
		try {
			while ((inputLine = br.readLine()) != null) {
				bufferStr.append(inputLine);
			}
			br.close();

			if (protocol.equalsIgnoreCase("reservation")) {
				JsonDTO reservationDTO = gson.fromJson(bufferStr.toString(), JsonDTO.class);
				try {
					customerReservationDAO.reservation(reservationDTO.getCustomerId(),
							reservationDTO.getRestaurantId());
					response = "예약 성공";
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (protocol.equalsIgnoreCase("cancel")) {
				JsonDTO cancelDTO = gson.fromJson(bufferStr.toString(), JsonDTO.class);
				try {
					customerReservationDAO.cancel(cancelDTO.getCustomerId(), cancelDTO.getRestaurantId());
					response = "예약 취소 성공";
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			byte[] bytes = response.getBytes();
			exchange.setAttribute("Content-Type", "text/plain; charset=UTF-8");
			exchange.sendResponseHeaders(200, bytes.length);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
			writer.write(response);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
