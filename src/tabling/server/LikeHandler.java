package tabling.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import tabling.dao.LikeDAO;
import tabling.dto.JsonDTO;

public class LikeHandler implements HttpHandler {

	private LikeDAO dao;
	private Gson gson;

	public LikeHandler() {
		dao = new LikeDAO();
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
				// 키값에 대한 벨류값 초기화
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
				// 좋아요 여부 확인 요청 응답
				if (type.equalsIgnoreCase("getLike")) {
					boolean like = dao.getLike(customerId, restaurantId);
					response = String.valueOf(like);
					// 좋아요 취소 요청 응답
				} else if (type.equalsIgnoreCase("delete")) {
					int likeCount = dao.deleteLike(customerId, restaurantId);
					response = String.valueOf(likeCount);
					// 좋아요 수 확인 요청 응답
				} else if (type.equalsIgnoreCase("getLikeCount")) {
					int likeCount = dao.getLikeCount(restaurantId);
					response = String.valueOf(likeCount);
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

			// 좋아요 요청 응답
			if (protocol.equalsIgnoreCase("insert")) {
				JsonDTO insertDTO = gson.fromJson(bufferStr.toString(), JsonDTO.class);
				try {
					int count = dao.addLike(insertDTO.getCustomerId(), insertDTO.getRestaurantId());
					response = String.valueOf(count);
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
