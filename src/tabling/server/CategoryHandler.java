package tabling.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import tabling.dao.CategoryDAO;

/**
 * 현재 사용 하지 않음
 */
public class CategoryHandler implements HttpHandler {

	private CategoryDAO dao;
	
	public CategoryHandler() {
		dao = new CategoryDAO();
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String method = exchange.getRequestMethod();
		if ("GET".equalsIgnoreCase(method)) {
			// GET 요청시 여기서 동작
			handleGetRequest(exchange);
		} else {
			// GET이 아닌 요청시
		}
	}
	
	// GET 요청시 동작
	private void handleGetRequest(HttpExchange exchange) {
		URI uri = exchange.getRequestURI();
		String path = uri.getPath();
		String response = null;
		String[] pathSegments = path.split("/");
		if (pathSegments.length >= 3) {
			int categoryId = Integer.parseInt(pathSegments[2]);
			try {
				response = dao.getCategoryName(categoryId);
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
