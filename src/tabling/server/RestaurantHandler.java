package tabling.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import tabling.dao.RestaurantDAO;
import tabling.dto.RestaurantDTO;
import tabling.util.Time;

public class RestaurantHandler implements HttpHandler {

	private RestaurantDAO restaurantDAO;
	private Gson gson;

	public RestaurantHandler() {
		restaurantDAO = new RestaurantDAO();
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String method = exchange.getRequestMethod();
		if ("GET".equalsIgnoreCase(method)) {
			// GET 요청시 여기서 동작
			handleGetRequest(exchange);
		} else {
			// GET 요청 이외
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
				// 키값에 대한 벨류값 초기화
				int customerId = 0;
				int categoryId = 0;
				int locationId = 0;
				int restaurantId = 0;
				String[] pairs = query.split("&");
				for (String pair : pairs) {
					String[] keyValue = pair.split("=");
					if (keyValue[0].equalsIgnoreCase("customerId")) {
						customerId = Integer.parseInt(keyValue[1]);
					} else if (keyValue[0].equalsIgnoreCase("categoryId")) {
						categoryId = Integer.parseInt(keyValue[1]);
					} else if (keyValue[0].equalsIgnoreCase("locationId")) {
						locationId = Integer.parseInt(keyValue[1]);
					} else if (keyValue[0].equalsIgnoreCase("restaurantId")) {
						restaurantId = Integer.parseInt(keyValue[1]);
					} else if (keyValue[0].equalsIgnoreCase("open")) {
						restaurantDAO.setOpenFilter(Boolean.parseBoolean(keyValue[1]));
					} else if (keyValue[0].equalsIgnoreCase("like")) {
						restaurantDAO.setLikeFilter(Boolean.parseBoolean(keyValue[1]));
					}
				}
				// 현재 시간 설정
				restaurantDAO.setCurrentTime(
						new Time(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getDayOfWeek().toString()));
				// 모든 식당 리스트 요청 응답
				if (type.equalsIgnoreCase("all")) {
					List<RestaurantDTO> list = restaurantDAO.getAllRestaurants(customerId);
					response = gson.toJson(list);
					// 해당 카테고리의 식당 리스트 요청 응답
				} else if (type.equalsIgnoreCase("category")) {
					List<RestaurantDTO> list = restaurantDAO.getRestaurantsByCategory(categoryId, customerId);
					response = gson.toJson(list);
					// 해당 지역의 식당 리스트 요청 응답
				} else if (type.equalsIgnoreCase("location")) {
					List<RestaurantDTO> list = restaurantDAO.getRestaurantsByLocation(locationId, customerId);
					response = gson.toJson(list);
					// 식당 정보 요청 응답
				} else if (type.equalsIgnoreCase("restaurant")) {
					RestaurantDTO dto = restaurantDAO.getRestaurantByRestaurantId(restaurantId);
					response = gson.toJson(dto);
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

}
